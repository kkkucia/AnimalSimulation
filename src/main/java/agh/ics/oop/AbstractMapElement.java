package agh.ics.oop;

abstract public class AbstractMapElement implements MapElement {
    Vector2d position;
    int zIndex;

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }
}
