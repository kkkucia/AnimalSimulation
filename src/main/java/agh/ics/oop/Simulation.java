package agh.ics.oop;

import java.util.List;

public interface Simulation {

    void animalGetsOlder();

    void removeDeadAnimals();

    void moveAllAnimals();

    void grassConsumption();

    void animalsProcreation();

    void grassGrowth();

    Animal[] competition(List<Animal> animals);
}
