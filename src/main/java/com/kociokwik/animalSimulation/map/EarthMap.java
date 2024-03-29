package com.kociokwik.animalSimulation.map;

import com.kociokwik.animalSimulation.map.element.Animal;
import com.kociokwik.animalSimulation.map.element.genome.Rotation;
import com.kociokwik.animalSimulation.settings.Vector2d;
import com.kociokwik.animalSimulation.settings.WorldParameters;

public class EarthMap extends AbstractWordMap {

    public EarthMap(WorldParameters worldParams, Grassfield grassfield) {
        super(worldParams, grassfield);
    }

    @Override
    public void animalCrossesBoundaries(Animal animal, Vector2d newPosition) {
        if (newPosition.x() < bottomLeftCorner.x()) {
            animal.move(new Vector2d(topRightCorner.x(), animal.getPosition().y()));
        } else if (newPosition.x() > topRightCorner.x()) {
            animal.move(new Vector2d(bottomLeftCorner.x(), animal.getPosition().y()));
        } else {
            animal.rotate(Rotation.Rotation180);
        }
    }
}
