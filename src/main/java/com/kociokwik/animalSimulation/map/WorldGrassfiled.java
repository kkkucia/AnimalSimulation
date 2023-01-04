package com.kociokwik.animalSimulation.map;

import com.kociokwik.animalSimulation.settings.Vector2d;

public interface WorldGrassfiled {

    void addGrassesToMap(int quantityOfGrass);

    void removeGrass(Vector2d position);
}
