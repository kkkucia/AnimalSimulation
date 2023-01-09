package com.kociokwik.animalSimulation.map;

import com.kociokwik.animalSimulation.map.element.Animal;
import com.kociokwik.animalSimulation.map.element.MapElement;
import com.kociokwik.animalSimulation.settings.PositionChangeObserver;
import com.kociokwik.animalSimulation.settings.Vector2d;
import com.kociokwik.animalSimulation.settings.WorldParameters;

import java.util.LinkedList;
import java.util.List;

abstract public class AbstractWordMap implements WorldMap, PositionChangeObserver {

    protected final Vector2d topRightCorner;
    protected final Vector2d bottomLeftCorner;

    protected List<Animal>[][] animalsOnMap;
    protected Grassfield grassfield;
    protected final WorldParameters worldParams;

    public AbstractWordMap(WorldParameters worldParams, Grassfield grassfield) {
        bottomLeftCorner = new Vector2d(0, 0);
        topRightCorner = new Vector2d(worldParams.width - 1, worldParams.height - 1);
        this.grassfield = grassfield;
        this.worldParams = worldParams;

        animalsOnMap = new List[worldParams.height][worldParams.width];

        for (int y = 0; y < animalsOnMap.length; y++) {
            for (int x = 0; x < animalsOnMap[y].length; x++) {
                animalsOnMap[y][x] = new LinkedList<Animal>();
            }
        }
    }

    public List<Animal>[][] getAnimalsOnMap() {
        return animalsOnMap;
    }

    @Override
    public boolean willAnimalCrossBoundaries(Vector2d field) {
        return !(field.precedes(topRightCorner) && field.follows(bottomLeftCorner));
    }

    @Override
    public Vector2d getTopRightCorner() {
        return topRightCorner;
    }

    @Override
    public Vector2d getBottomLeftCorner() {
        return bottomLeftCorner;
    }

    @Override
    public void placeAnimal(Animal animal) {
        animalsOnMap[animal.getPosition().y()][animal.getPosition().x()].add(animal);
    }

    @Override
    public void removeAnimal(Animal animal) {
        animalsOnMap[animal.getPosition().y()][animal.getPosition().x()].remove(animal);
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (!animalsOnMap[position.y()][position.x()].isEmpty() && !grassfield.grassesOnMap.containsKey(position)) {
            return animalsOnMap[position.y()][position.x()].get(0);
        }
        return null;
    }

    @Override
    public void animalMoved(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        animalsOnMap[oldPosition.y()][oldPosition.x()].remove(animal);
        animalsOnMap[newPosition.y()][newPosition.x()].add(animal);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public String toString() {
        return new MapVisualiser(this).draw(bottomLeftCorner, topRightCorner);
    }
}
