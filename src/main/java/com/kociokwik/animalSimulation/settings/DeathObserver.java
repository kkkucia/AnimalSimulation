package com.kociokwik.animalSimulation.settings;

import com.kociokwik.animalSimulation.map.element.Animal;

public interface DeathObserver {
    void animalDied(Animal animal);
}
