package agh.ics.oop;

public class GenomeParameters {
    public int genomeLength;
    public int behaviourPercent;
    public MutationType mutationType;
    public int minPossibleMutationsNumber;
    public int maxPossibleMutationsNumber;

    public GenomeParameters(int genomeLength, int behaviourPercent, MutationType mutationType, int minPossibleMutationsNumber, int maxPossibleMutationsNumber) {
        this.genomeLength = genomeLength;
        this.behaviourPercent = behaviourPercent;
        this.mutationType = mutationType;
        this.minPossibleMutationsNumber = minPossibleMutationsNumber;
        this.maxPossibleMutationsNumber = maxPossibleMutationsNumber;
    }
}
