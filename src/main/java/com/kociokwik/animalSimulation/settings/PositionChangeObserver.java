package com.kociokwik.animalSimulation.settings;

import com.kociokwik.animalSimulation.map.element.Animal;

public interface PositionChangeObserver {
    void animalMoved(Animal animal, Vector2d oldPosition, Vector2d newPosition);
}
