package com.kociokwik.animalSimulation.settings;

import com.kociokwik.animalSimulation.map.element.genome.MutationType;

public class GenomeParametersBuilder {
    private Integer genomeLength;
    private Integer behaviourPercent;
    private MutationType mutationType;
    private Integer minPossibleMutationsNumber;
    private Integer maxPossibleMutationsNumber;

    public GenomeParameters build() {
        if (genomeLength == null || genomeLength < 0) {
            throw new WrongParameterException("Length of geome");
        }
        if (behaviourPercent == null || behaviourPercent < 0) {
            throw new WrongParameterException("Behaviour");
        }
        if (mutationType == null) {
            throw new WrongParameterException("Mutation type");
        }
        if (minPossibleMutationsNumber == null || minPossibleMutationsNumber < 0) {
            throw new WrongParameterException("Minimal mutation quantity");
        }
        if (maxPossibleMutationsNumber == null || maxPossibleMutationsNumber < 0) {
            throw new WrongParameterException("Maximum mutation quantity");
        }
        return new GenomeParameters(genomeLength, behaviourPercent, mutationType, minPossibleMutationsNumber, maxPossibleMutationsNumber);
    }

    public GenomeParametersBuilder setGenomeLength(Integer genomeLength) {
        this.genomeLength = genomeLength;
        return this;
    }

    public GenomeParametersBuilder setBehaviourPercent(Integer behaviourPercent) {
        this.behaviourPercent = behaviourPercent;
        return this;
    }

    public GenomeParametersBuilder setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
        return this;
    }

    public GenomeParametersBuilder setMinPossibleMutationsNumber(Integer minPossibleMutationsNumber) {
        this.minPossibleMutationsNumber = minPossibleMutationsNumber;
        return this;
    }

    public GenomeParametersBuilder setMaxPossibleMutationsNumber(Integer maxPossibleMutationsNumber) {
        this.maxPossibleMutationsNumber = maxPossibleMutationsNumber;
        return this;
    }
}
