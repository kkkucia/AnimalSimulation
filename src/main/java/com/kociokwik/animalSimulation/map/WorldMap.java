package com.kociokwik.animalSimulation.map;

import com.kociokwik.animalSimulation.map.element.Animal;
import com.kociokwik.animalSimulation.settings.Vector2d;

public interface WorldMap {
    boolean willAnimalCrossBoundaries(Vector2d field);

    public void animalCrossesBoundaries(Animal animal, Vector2d newPosition);

    void placeAnimal(Animal animal);

    void removeAnimal(Animal animal);

    boolean isOccupied(Vector2d position);

    Object objectAt(Vector2d position);

    Vector2d getTopRightCorner();

    Vector2d getBottomLeftCorner();
}
