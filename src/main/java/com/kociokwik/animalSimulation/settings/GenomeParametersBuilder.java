package com.kociokwik.animalSimulation.settings;

import com.kociokwik.animalSimulation.map.element.genome.MutationType;

public class GenomeParametersBuilder {
    private Integer genomeLength;
    private Integer behaviourPercent;
    private MutationType mutationType;
    private Integer minPossibleMutationsNumber;
    private Integer maxPossibleMutationsNumber;

    public GenomeParameters build() {
        if (genomeLength == null) {
            throw new WrongParameterException("genomeLength");
        }
        if (behaviourPercent == null) {
            throw new WrongParameterException("behaviourPercent");
        }
        if (mutationType == null) {
            throw new WrongParameterException("genomeLength");
        }
        if (minPossibleMutationsNumber == null) {
            throw new WrongParameterException("genomeLength");
        }
        if (maxPossibleMutationsNumber == null) {
            throw new WrongParameterException("genomeLength");
        }
        return new GenomeParameters(genomeLength, behaviourPercent, mutationType, minPossibleMutationsNumber, maxPossibleMutationsNumber);
    }

    public GenomeParametersBuilder setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
        return this;
    }

    public GenomeParametersBuilder setBehaviourPercent(int behaviourPercent) {
        this.behaviourPercent = behaviourPercent;
        return this;
    }

    public GenomeParametersBuilder setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
        return this;
    }

    public GenomeParametersBuilder setMinPossibleMutationsNumber(int minPossibleMutationsNumber) {
        this.minPossibleMutationsNumber = minPossibleMutationsNumber;
        return this;
    }

    public GenomeParametersBuilder setMaxPossibleMutationsNumber(int maxPossibleMutationsNumber) {
        this.maxPossibleMutationsNumber = maxPossibleMutationsNumber;
        return this;
    }
}
