package com.kociokwik.animalSimulation.map.element.genome;

import com.kociokwik.animalSimulation.settings.GenomeParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.util.Collections.shuffle;

public class GeneSequence {
    private final Rotation[] sequence;
    private int currentGeneIndex;
    private final GenomeParameters params;

    //start animal, gene sequence created randomly
    public GeneSequence(GenomeParameters params) {
        this.params = params;
        sequence = new Rotation[params.genomeLength];
        currentGeneIndex = new Random().nextInt(Rotation.numberOfGenes());

        for (int i = 0; i < params.genomeLength; i++) {
            sequence[i] = randomGene();
        }
    }

    //newborn animal
    private GeneSequence(Rotation[] sequence, GenomeParameters params) {
        this.sequence = sequence;
        this.params = params;
        currentGeneIndex = new Random().nextInt(Rotation.numberOfGenes());
    }

    public void nextGene() {
        int randomNum = new Random().nextInt(100);
        boolean crazy = !(randomNum < params.behaviourPercent);

        if (crazy) {
            int[] toLottery = new int[sequence.length - 1];
            int j = 0;
            for (int i = 0; i < sequence.length; i++) {
                if (i != currentGeneIndex) {
                    toLottery[j] = i;
                    j += 1;
                }
            }
            currentGeneIndex = toLottery[new Random().nextInt(toLottery.length)];
        } else {
            currentGeneIndex = (currentGeneIndex + 1) % sequence.length;
        }
    }

    private Rotation randomGene() {
        int numberOfGenes = Rotation.numberOfGenes();
        return Rotation.encryptGene(new Random().nextInt(numberOfGenes));
    }

    //always used on stronger parent
    public GeneSequence crossBreed(GeneSequence weakerParentGenome, int lengthOfStrongerGenome) {
        //false - left, true - right
        boolean strongerSide = new Random().nextBoolean();
        Rotation[] part1;
        Rotation[] part2;

        if (strongerSide) {
            part1 = partOfGenome(weakerParentGenome.getSequence(), sequence.length - lengthOfStrongerGenome, false);
            part2 = partOfGenome(sequence, lengthOfStrongerGenome, true);
        } else {
            part1 = partOfGenome(sequence, lengthOfStrongerGenome, true);
            part2 = partOfGenome(weakerParentGenome.getSequence(), sequence.length - lengthOfStrongerGenome, false);
        }

        Rotation[] newSequence = new Rotation[sequence.length];
        System.arraycopy(part1, 0, newSequence, 0, part1.length);
        System.arraycopy(part2, 0, newSequence, part1.length, part2.length);

        if (params.mutationType == MutationType.Strict) {
            Rotation[] newSeq = mixedSequenceStrict(newSequence);
            return new GeneSequence(newSeq, params);
        }
        return new GeneSequence(mixedSequenceLottery(newSequence), params);
    }

    private Rotation[] partOfGenome(Rotation[] sequence, int size, boolean side) {
        if (side) {
            return Arrays.copyOfRange(sequence, sequence.length - size, sequence.length);
        }
        return Arrays.copyOfRange(sequence, 0, size);
    }

    private Rotation[] mixedSequenceLottery(Rotation[] sequence) {
        int numberOfChanges = new Random().nextInt(params.maxPossibleMutationsNumber + 1 - params.minPossibleMutationsNumber) + params.minPossibleMutationsNumber;
        ArrayList<Integer> indexes = shuffledIndexes();

        for (int i = 0; i < numberOfChanges; i++) {
            sequence[indexes.get(i)] = randomGene();
        }
        return sequence;
    }

    private Rotation[] mixedSequenceStrict(Rotation[] sequence) {
        int numberOfChanges = new Random().nextInt(params.maxPossibleMutationsNumber + 1 - params.minPossibleMutationsNumber) + params.minPossibleMutationsNumber;
        ArrayList<Integer> indexes = shuffledIndexes();

        for (int i = 0; i < numberOfChanges; i++) {
            int geneChange = new Random().nextBoolean() ? 1 : -1;
            int geneValue = (sequence[i].geneValue() + geneChange) % Rotation.numberOfGenes();
            sequence[indexes.get(i)] = geneValue < 0 ? Rotation.encryptGene(7) : Rotation.encryptGene(geneValue);
        }
        return sequence;
    }

    private ArrayList<Integer> shuffledIndexes() {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < params.genomeLength; i++) {
            indexes.add(i);
        }
        shuffle(indexes);

        return indexes;
    }

    public Rotation[] getSequence() {
        return sequence;
    }

    public Rotation getCurrentGene() {
        return sequence[currentGeneIndex % sequence.length];
    }

    @Override
    public String toString() {
        return Arrays.toString(sequence);
    }
}
