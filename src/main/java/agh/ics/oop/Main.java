package agh.ics.oop;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        GeneSequence gene1 = new GeneSequence(MutationType.Strict, 10, 0);
        GeneSequence gene2 = new GeneSequence(MutationType.Strict, 10, 0);

        System.out.println(Arrays.toString(gene1.getSequence()));
        System.out.println(gene1.getCurrentGene());
        gene1.nextGene();
        System.out.println(gene1.getCurrentGene());
        gene1.nextGene();
        System.out.println(gene1.getCurrentGene());
        gene1.nextGene();
        System.out.println(gene1.getCurrentGene());
        gene1.nextGene();
        System.out.println(gene1.getCurrentGene());
        gene1.nextGene();
        System.out.println(gene1.getCurrentGene());
        gene1.nextGene();
        System.out.println(gene1.getCurrentGene());

    }
}
