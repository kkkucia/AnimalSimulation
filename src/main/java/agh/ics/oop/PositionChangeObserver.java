package agh.ics.oop;

public interface PositionChangeObserver {
    void animalMoved(Vector2d oldPosition, Vector2d newPosition);
}
