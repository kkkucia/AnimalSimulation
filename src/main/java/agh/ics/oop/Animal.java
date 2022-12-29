package agh.ics.oop;

public class Animal extends AbstractMapElement{
    GeneSequence gene;
    int age;
    int kids;
    int energy;
    public Animal(Vector2d startPosition, MapDirection startDirection, int startEnergy, GeneSequence gene) {
        position = startPosition;
        MapDirection direction = startDirection;
        energy = startEnergy;
        this.gene = gene;
        zIndex = 1;
        age = 0;
        kids = 0;
    }
}
