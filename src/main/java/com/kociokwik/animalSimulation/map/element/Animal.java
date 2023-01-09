package com.kociokwik.animalSimulation.map.element;

import com.kociokwik.animalSimulation.map.AbstractWordMap;
import com.kociokwik.animalSimulation.map.element.genome.GeneSequence;
import com.kociokwik.animalSimulation.map.element.genome.Rotation;
import com.kociokwik.animalSimulation.settings.DeathObserver;
import com.kociokwik.animalSimulation.settings.PositionChangeObserver;
import com.kociokwik.animalSimulation.settings.Vector2d;

import java.util.LinkedList;
import java.util.Random;

public class Animal extends AbstractMapElement {
    private final GeneSequence genome;

    private Rotation currentRotation;
    private int age;
    private int kidsQuantity;
    private int energy;
    private LinkedList<PositionChangeObserver> observersList = new LinkedList<PositionChangeObserver>();
    private LinkedList<DeathObserver> deathObservers = new LinkedList<DeathObserver>();
    private final AbstractWordMap map;

    public void addObserver(PositionChangeObserver observer) {
        observersList.add(observer);
    }

    public void addDeathObserver(DeathObserver observer) {
        deathObservers.add(observer);
    }

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

    public Animal(Vector2d startPosition, int startEnergy, GeneSequence genome, AbstractWordMap map) {
        position = startPosition;
        currentRotation = Rotation.encryptGene(new Random().nextInt(Rotation.numberOfGenes()));
        energy = startEnergy;
        this.genome = genome;
        zIndex = 1;
        age = 0;
        kidsQuantity = 0;
        this.map = map;
    }

    public void addEnergy(int energyBoost) {
        energy += energyBoost;
    }

    public void addChild() {
        kidsQuantity += 1;
    }

    public void loseEnergy(int energyLoss) {
        energy -= energyLoss;
        if (energy <= 0) {
            die();
        }
    }

    public void getOlder() {
        age += 1;
    }

    public void die() {
        for (DeathObserver observer : deathObservers) {
            observer.animalDied(this);
        }
    }

    public boolean ableToProcreate(int energyToProcreate) {
        return energy >= energyToProcreate;
    }

    public void move(Vector2d newPosition) {
        if (!map.willAnimalCrossBoundaries(newPosition)) {
            observersList.get(0).animalMoved(this, this.position, newPosition);
            position = newPosition;
        } else {
            map.animalCrossesBoundaries(this, newPosition);
        }
    }

    public void rotate(Rotation rotation) {
        currentRotation = Rotation.encryptGene((currentRotation.geneValue() + rotation.geneValue()) % Rotation.numberOfGenes());
    }

    @Override
    public String toString() {
        return currentRotation.toString();
    }

    @Override
    public String getPicture() {
        return "src/main/resources/images/cat.png";
    }


    public Rotation getCurrentRotation() {
        return currentRotation;
    }
}
