package agh.ics.oop;

public interface Map extends PositionChangeObserver{
    boolean canMoveTo(Vector2d field);
    boolean animalCrossesBoundaries(Animal animal);
}
