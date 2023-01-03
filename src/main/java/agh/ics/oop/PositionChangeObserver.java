package agh.ics.oop;

public interface PositionChangeObserver {
    void animalMoved(Animal animal, Vector2d oldPosition, Vector2d newPosition);
}
