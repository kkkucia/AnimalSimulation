package com.kociokwik.animalSimulation.map.element;

import com.kociokwik.animalSimulation.settings.Vector2d;

abstract public class AbstractMapElement implements MapElement {
    protected Vector2d position;
    protected int zIndex;

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }
}
