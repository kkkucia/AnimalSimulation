package agh.ics.oop;

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
        animal.loseEnergy(worldParams.energyToProcreation);
    }
}