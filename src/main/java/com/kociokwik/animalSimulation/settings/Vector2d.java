package com.kociokwik.animalSimulation.settings;

import java.util.Objects;

public record Vector2d(int x, int y) {

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return x >= other.x && y >= other.y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(other.x + x, other.y + y);
    }

    @Override

    public boolean equals(Object other) {
        if (!(other instanceof Vector2d that)) {
            return false;
        }
        return that.x == x && that.y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
