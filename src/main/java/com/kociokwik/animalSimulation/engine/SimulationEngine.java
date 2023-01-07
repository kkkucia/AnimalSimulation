package com.kociokwik.animalSimulation.engine;

import com.kociokwik.animalSimulation.map.*;
import com.kociokwik.animalSimulation.map.element.Animal;
import com.kociokwik.animalSimulation.map.element.genome.GeneSequence;
import com.kociokwik.animalSimulation.settings.GenomeParameters;
import com.kociokwik.animalSimulation.settings.DeathObserver;
import com.kociokwik.animalSimulation.settings.Vector2d;
import com.kociokwik.animalSimulation.settings.WorldParameters;

import java.util.*;

public class SimulationEngine implements Simulation, DeathObserver {

    private List<Animal> animalCorpses;
    private final AbstractWordMap map;

    private final Grassfield grassfield;
    private final WorldParameters worldParams;
    private final GenomeParameters genomeParams;
    private final int ENERGY_LOST_PER_DAY = 1;

    public SimulationEngine(WorldParameters worldParams, GenomeParameters genomeParams) {
        this.worldParams = worldParams;
        this.genomeParams = genomeParams;
        animalCorpses = new LinkedList<>();
        grassfield = worldParams.grassfiledType == GrassfieldType.ToxicCorpses ? new ToxicCorpses(worldParams) : new ForestedEquators(worldParams);
        map = worldParams.mapType == MapType.EarthMap ? new EarthMap(worldParams, grassfield) : new HellMap(worldParams, grassfield);

        addAnimalsToMap();
    }

    public AbstractWordMap getMap() {
        return map;
    }

    private void addAnimalsToMap() {
        for (int i = 0; i < worldParams.startQuantityOfAnimals; i++) {
            Vector2d startPosition = new Vector2d(new Random().nextInt(worldParams.width), new Random().nextInt(worldParams.height));
            Animal animal = new Animal(startPosition, worldParams.startEnergy, new GeneSequence(genomeParams), map);
            addAnimalToMap(animal);
        }
    }

    private void addAnimalToMap(Animal animal) {
        map.placeAnimal(animal);
        animal.addObserver(map);
        animal.addDeathObserver(this);
    }

    @Override
    public void animalGetsOlder() {
        for (List<Animal>[] tab : map.getAnimalsOnMap()) {
            for (List<Animal> animalsOnSingleFiled : tab) {
                for (Animal animal : animalsOnSingleFiled) {
                    animal.loseEnergy(ENERGY_LOST_PER_DAY);
                    animal.getOlder();
                }
            }
        }
    }

    @Override
    public void animalDied(Animal animal) {
        animalCorpses.add(animal);
    }

    @Override
    public void removeDeadAnimals() {
        for (Animal animalCorps : animalCorpses) {
            map.removeAnimal(animalCorps);
        }
        animalCorpses = new LinkedList<>();
    }

    @Override
    public void rotateAndMoveAllAnimals() {
        LinkedHashMap<Animal, Vector2d> animalsMovement = new LinkedHashMap<>();

        for (List<Animal>[] tab : map.getAnimalsOnMap()) {
            for (List<Animal> animalsOnSingleFiled : tab) {
                for (Animal animal : animalsOnSingleFiled) {
                    animal.rotate(animal.getGenome().getCurrentGene());
                    Vector2d newPosition = animal.getPosition().add(animal.getGenome().getCurrentGene().vectorValue());
                    animalsMovement.put(animal, newPosition);
                    animal.loseEnergy(worldParams.energyLossPerMove);
                }
            }
        }
        for (Map.Entry<Animal, Vector2d> entry : animalsMovement.entrySet()) {
            Animal animal = entry.getKey();
            animal.move(entry.getValue());
        }
    }

    @Override
    public void grassConsumption() {
        for (List<Animal>[] tab : map.getAnimalsOnMap()) {
            for (List<Animal> animalsOnSingleFiled : tab) {
                Animal[] sortedAnimals = competition(animalsOnSingleFiled);
                if (sortedAnimals.length >= 1) {
                    Animal animal = sortedAnimals[0];
                    if (grassfield.getGrassesOnMap().containsKey(animal.getPosition())) {
                        animal.addEnergy(worldParams.energyFromGrass);
                        grassfield.removeGrass(animal.getPosition());
                    }
                }
            }
        }
    }

    @Override
    public void animalsProcreation() {
        for (List<Animal>[] tab : map.getAnimalsOnMap()) {
            for (List<Animal> animalsOnSingleFiled : tab) {
                Animal[] sortedAnimals = competition(animalsOnSingleFiled);
                if (sortedAnimals.length >= 2) {
                    if (sortedAnimals[1].ableToProcreate(worldParams.energyFullStomach)) {
                        animalBirth(sortedAnimals[0], sortedAnimals[1]);
                        sortedAnimals[0].addChild();
                        sortedAnimals[1].addChild();
                        sortedAnimals[0].loseEnergy(worldParams.energyLostWhileProcreation);
                        sortedAnimals[1].loseEnergy(worldParams.energyLostWhileProcreation);
                    }
                }
            }
        }
    }

    private void animalBirth(Animal strongerParent, Animal weakerParent) {
        int lengthOfStrongerGenome = (int) ((strongerParent.getEnergy() / (float) (weakerParent.getEnergy() + strongerParent.getEnergy())) * genomeParams.genomeLength);

        GeneSequence childGenome = strongerParent.getGenome().crossBreed(weakerParent.getGenome(), lengthOfStrongerGenome);

        Animal newbornAnimal = new Animal(strongerParent.getPosition(), worldParams.energyLostWhileProcreation * 2, childGenome, map);
        addAnimalToMap(newbornAnimal);
    }

    @Override
    public void dailyGrassGrowth() {
        grassfield.addGrassesToMap(worldParams.quantityGrassPerDay);
    }

    @Override
    public Animal[] competition(List<Animal> animals) {
        animals.sort(new Comparator<Animal>() {
            @Override
            public int compare(Animal a, Animal b) {
                if (a.getEnergy() != b.getEnergy()) {
                    return b.getEnergy() - a.getEnergy();
                }
                if (a.getAge() != b.getAge()) {
                    return b.getAge() - a.getAge();
                }
                if (a.getKidsQuantity() != b.getKidsQuantity()) {
                    return b.getKidsQuantity() - a.getKidsQuantity();
                }
                return new Random().nextInt(2);
            }
        });
        return animals.toArray(Animal[]::new);
    }

    public Grassfield getGrassfield() {
        return grassfield;
    }
}
