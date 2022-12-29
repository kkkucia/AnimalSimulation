package agh.ics.oop;

public enum RotationGene {
    Gene0,
    Gene1,
    Gene2,
    Gene3,
    Gene4,
    Gene5,
    Gene6,
    Gene7;

    public static int numberOfGenes(){
        return RotationGene.values().length;
    }

    public int geneValue(){
        return switch (this){
            case Gene0 -> 0;
            case Gene1 -> 1;
            case Gene2 -> 2;
            case Gene3 -> 3;
            case Gene4 -> 4;
            case Gene5 -> 5;
            case Gene6 -> 6;
            case Gene7 -> 7;
        };
    }

    public static RotationGene encryptGene(int value){
        return switch (value){
            case 0 -> Gene0;
            case 1 -> Gene1;
            case 2 -> Gene2;
            case 3 -> Gene3;
            case 4 -> Gene4;
            case 5 -> Gene5;
            case 6 -> Gene6;
            case 7 -> Gene7;
            default -> null;
        };
    }
}
