package agh.ics.oop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class AbstractWordMap implements WorldMap, PositionChangeObserver {

    protected final Vector2d topRightCorner;
    protected final Vector2d bottomLeftCorner;

    protected Map<Vector2d, List<MapElement>> elementsOnMap = new HashMap<>();
    protected Map<Vector2d, List<Animal>> animalsOnMap = new HashMap<>();
    protected Grassfield grassfield;
    WorldParameters worldParams;

    public AbstractWordMap(WorldParameters worldParams, Grassfield grassfield) {
        bottomLeftCorner = new Vector2d(0, 0);
        topRightCorner = new Vector2d(worldParams.width, worldParams.height);
        this.grassfield = grassfield;
        this.worldParams = worldParams;
    }

    public Map<Vector2d, List<MapElement>> getElementsOnMap() {
        return elementsOnMap;
    }

    public Map<Vector2d, List<Animal>> getAnimalsOnMap() {
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
        elementsOnMap.get(animal.getPosition()).add(animal);
        animalsOnMap.get(animal.getPosition()).add(animal);
    }

    @Override
    public void removeAnimal(Animal animal) {
        elementsOnMap.get(animal.getPosition()).remove(animal);
        animalsOnMap.get(animal.getPosition()).remove(animal);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }

    @Override
    public void animalMoved(Vector2d oldPosition, Vector2d newPosition) {

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }
}
