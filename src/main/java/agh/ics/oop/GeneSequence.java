package agh.ics.oop;

import java.util.Arrays;
import java.util.Random;

public class GeneSequence {
    private final RotationGene[] sequence;
    private int currentGeneIndex;
    private final MutationType mutationType;
    private final int behaviourPercent;

    //start animal, gene sequence created randomly
    public GeneSequence(MutationType mutationType, int sequenceLength, int behaviourPercent) {
        this.mutationType = mutationType;
        this.behaviourPercent = behaviourPercent;
        sequence = new RotationGene[sequenceLength];
        currentGeneIndex = new Random().nextInt(RotationGene.numberOfGenes());

        for (int i = 0; i < sequenceLength; i++) {
            sequence[i] = randomGene();
        }
    }

    //newborn animal
    private GeneSequence(RotationGene[] sequence, MutationType mutationType, int behaviourPercent) {
        this.sequence = sequence;
        this.mutationType = mutationType;
        this.behaviourPercent = behaviourPercent;
        currentGeneIndex = new Random().nextInt(RotationGene.numberOfGenes());
    }

    public void nextGene() {
        int randomNum = new Random().nextInt(100);
        boolean crazy = !(randomNum < behaviourPercent);

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

    private RotationGene randomGene() {
        int numberOfGenes = RotationGene.numberOfGenes();
        return RotationGene.encryptGene(new Random().nextInt(numberOfGenes));
    }

    //always used on stronger parent
    public GeneSequence crossBreed(GeneSequence weakerParentGenome, int lengthOfStrongerGenome) {
        //false - left, true - right
        boolean strongerSide = new Random().nextBoolean();
        RotationGene[] part1;
        RotationGene[] part2;

        if (strongerSide) {
            part1 = partOfGenome(weakerParentGenome.getSequence(), sequence.length - lengthOfStrongerGenome, false);
            part2 = partOfGenome(sequence, lengthOfStrongerGenome, true);
        } else {
            part1 = partOfGenome(sequence, lengthOfStrongerGenome, true);
            part2 = partOfGenome(weakerParentGenome.getSequence(), sequence.length - lengthOfStrongerGenome, false);
        }

        RotationGene[] newSequence = new RotationGene[sequence.length];
        System.arraycopy(part1, 0, newSequence, 0, part1.length);
        System.arraycopy(part2, 0, newSequence, part1.length, part2.length);

        if (mutationType == MutationType.Strict) {
            return new GeneSequence(mixedSequenceStrict(newSequence), mutationType, behaviourPercent);
        }
        return new GeneSequence(mixedSequenceLottery(newSequence), mutationType, behaviourPercent);
    }

    private RotationGene[] partOfGenome(RotationGene[] sequence, int size, boolean side) {
        if (side) {
            return Arrays.copyOfRange(sequence, sequence.length - size, sequence.length);
        }
        return Arrays.copyOfRange(sequence, 0, size);
    }

    private RotationGene[] mixedSequenceLottery(RotationGene[] sequence) {
        for (int i = 0; i < sequence.length; i++) {
            if (new Random().nextBoolean()) {
                sequence[i] = randomGene();
            }
        }
        return sequence;
    }

    private RotationGene[] mixedSequenceStrict(RotationGene[] sequence) {
        for (int i = 0; i < sequence.length; i++) {
            int geneValue = (sequence[i].geneValue() + new Random().nextInt(2 - (-1)) + (-1)) % RotationGene.numberOfGenes();
            sequence[i] = RotationGene.encryptGene(geneValue);
        }
        return sequence;
    }

    public RotationGene[] getSequence() {
        return sequence;
    }

    public RotationGene getCurrentGene() {
        return sequence[currentGeneIndex];
    }
}
