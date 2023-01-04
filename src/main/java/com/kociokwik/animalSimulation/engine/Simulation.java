package com.kociokwik.animalSimulation.engine;

import com.kociokwik.animalSimulation.map.element.Animal;

import java.util.List;

public interface Simulation {

    void animalGetsOlder();

    void removeDeadAnimals();

    void rotateAndMoveAllAnimals();

    void grassConsumption();

    void animalsProcreation();

    void dailyGrassGrowth();

    Animal[] competition(List<Animal> animals);
}
