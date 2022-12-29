package agh.ics.oop;

import java.util.Random;

public class Animal extends AbstractMapElement {
    GeneSequence genome;
    Rotation currentRotation;
    int age;
    int kidsQuantity;
    int energy;

    public GeneSequence getGenome() {
        return genome;
    }

    public int getEnergy() {
        return energy;
    }

    public int getKidsQuantity() {
        return kidsQuantity;
    }

    public int getAge() {
        return age;
    }

    public Animal(Vector2d startPosition, int startEnergy, GeneSequence genome) {
        position = startPosition;
        currentRotation = Rotation.encryptGene(new Random().nextInt(Rotation.numberOfGenes()));
        energy = startEnergy;
        this.genome = genome;
        zIndex = 1;
        age = 0;
        kidsQuantity = 0;
    }

    public void addEnergy(int energyBoost) {
        energy += energyBoost;
    }

    public void loseEnergy(int energyLoss) {
        energy -= energyLoss;
        if(energy <= 0) {
            die();
        }
    }

    public void die() {
        //DeathObserver.animalDied(this)
    }

    public boolean ableToProcreate(int energyToProcreate) {
        return energy >= energyToProcreate;
    }

    public void move(Vector2d newPosition) {
        position = newPosition;
    }

    public void rotate(Rotation rotation) {
        currentRotation = Rotation.encryptGene((currentRotation.geneValue() + rotation.geneValue()) % Rotation.numberOfGenes());
    }
}
