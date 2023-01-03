package agh.ics.oop;

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
