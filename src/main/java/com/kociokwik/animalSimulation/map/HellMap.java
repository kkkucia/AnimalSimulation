package com.kociokwik.animalSimulation.map;

import com.kociokwik.animalSimulation.map.element.Animal;
import com.kociokwik.animalSimulation.settings.Vector2d;
import com.kociokwik.animalSimulation.settings.WorldParameters;

import java.util.Random;

public class HellMap extends AbstractWordMap {
    public HellMap(WorldParameters worldParams, Grassfield grassfield) {
        super(worldParams, grassfield);
    }

    @Override
    public void animalCrossesBoundaries(Animal animal, Vector2d newPosition) {
        int newX = new Random().nextInt(topRightCorner.x() - bottomLeftCorner.x()) + bottomLeftCorner.x();
        int newY = new Random().nextInt(topRightCorner.y() - bottomLeftCorner.y()) + bottomLeftCorner.y();

        animal.move(new Vector2d(newX, newY));
        animal.loseEnergy(worldParams.energyLostWhileProcreation);
    }
}