package com.kociokwik.animalSimulation.map.element;

import com.kociokwik.animalSimulation.settings.Vector2d;

public interface MapElement {
    Vector2d getPosition();

    int getZIndex();
}
