package com.kociokwik.animalSimulation.map.element;

import com.kociokwik.animalSimulation.settings.Vector2d;

public class Grass extends AbstractMapElement {
    public Grass(Vector2d position) {
        this.position = position;
        this.zIndex = 0;
    }

    @Override
    public String toString() {
        return "*";
    }
}
