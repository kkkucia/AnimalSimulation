package agh.ics.oop;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements Simulation, DeathObserver {

    private List<Animal> animalCorpses;
    private final AbstractWordMap map;
    private final Grassfield grassfield;
    WorldParameters worldParams;
    GenomeParameters genomeParams;
    final int ENERGY_LOST_PER_DAY = 1;

    public SimulationEngine(WorldParameters worldParams, GenomeParameters genomeParams) {
        this.worldParams = worldParams;
        this.genomeParams = genomeParams;
        animalCorpses = new LinkedList<>();
        grassfield = worldParams.grassfiledType == GrassfieldType.ToxicCorpses ? new ToxicCorpses(worldParams) : new ForestedEquators(worldParams);
        map = worldParams.mapType == MapType.EarthMap ? new EarthMap(worldParams, grassfield):new HellMap(worldParams, grassfield);

        addAnimalsToMap();
    }

    private void addAnimalsToMap(){
        for (int i = 0; i < worldParams.startQuantityOfAnimals; i++){
            Vector2d startPosition = new Vector2d(new Random().nextInt(worldParams.width), new Random().nextInt(worldParams.height));
            Animal animal = new Animal(startPosition, worldParams.startEnergy, new GeneSequence(genomeParams));
            map.placeAnimal(animal);
        }
    }

    @Override
    public void animalGetsOlder() {
        for (List<Animal> animalsOnSingleFiled : map.animalsOnMap.values()) {
            for (Animal animal : animalsOnSingleFiled) {
                animal.loseEnergy(ENERGY_LOST_PER_DAY);
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
    public void moveAllAnimals() {
        for (List<Animal> animalsOnSingleFiled : map.animalsOnMap.values()) {
            for (Animal animal : animalsOnSingleFiled) {
                Vector2d newPosition = animal.getPosition().add(animal.getGenome().getCurrentGene().vectorValue());
                animal.move(newPosition);
                animal.loseEnergy(worldParams.energyLossPerMove);
            }
        }
    }

    @Override
    public void grassConsumption() {
    }

    @Override
    public void animalsProcreation() {
        for (List<Animal> animalsOnSingleFiled : map.animalsOnMap.values()) {
            Animal[] sortedAnimals = competition(animalsOnSingleFiled);
            if (sortedAnimals.length >= 2) {
                if (sortedAnimals[1].getEnergy() >= worldParams.energyFullStomach)
                    animalBirth(sortedAnimals[0], sortedAnimals[1]);
                sortedAnimals[0].loseEnergy(worldParams.energyToProcreation);
                sortedAnimals[1].loseEnergy(worldParams.energyToProcreation);
            }
        }
    }


    private void animalBirth(Animal strongerParent, Animal weakerParent) {
        int lengthOfStrongerGenome = strongerParent.getEnergy() / (weakerParent.getEnergy() + strongerParent.getEnergy()) * genomeParams.genomeLength;

        GeneSequence childGenome = strongerParent.getGenome().crossBreed(weakerParent.getGenome(), lengthOfStrongerGenome);

        Animal newbornAnimal = new Animal(strongerParent.getPosition(), worldParams.energyToProcreation * 2, childGenome);
        map.placeAnimal(newbornAnimal);
    }

    @Override
    public void grassGrowth() {

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

}
