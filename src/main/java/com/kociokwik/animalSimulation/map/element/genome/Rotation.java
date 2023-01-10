package com.kociokwik.animalSimulation.map.element.genome;

import com.kociokwik.animalSimulation.settings.Vector2d;

public enum Rotation {
    Rotation0,
    Rotation45,
    Rotation90,
    Rotation135,
    Rotation180,
    Rotation225,
    Rotation270,
    Rotation315;

    public static int numberOfGenes() {
        return Rotation.values().length;
    }

    public int geneValue() {
        return switch (this) {
            case Rotation0 -> 0;
            case Rotation45 -> 1;
            case Rotation90 -> 2;
            case Rotation135 -> 3;
            case Rotation180 -> 4;
            case Rotation225 -> 5;
            case Rotation270 -> 6;
            case Rotation315 -> 7;
        };
    }

    public static Rotation encryptGene(int value) {
        return switch (value) {
            case 0 -> Rotation0;
            case 1 -> Rotation45;
            case 2 -> Rotation90;
            case 3 -> Rotation135;
            case 4 -> Rotation180;
            case 5 -> Rotation225;
            case 6 -> Rotation270;
            case 7 -> Rotation315;
            default -> null;
        };
    }

    public Vector2d vectorValue() {
        return switch (this) {
            case Rotation0 -> new Vector2d(0, 1);
            case Rotation45 -> new Vector2d(1, 1);
            case Rotation90 -> new Vector2d(1, 0);
            case Rotation135 -> new Vector2d(1, -1);
            case Rotation180 -> new Vector2d(0, -1);
            case Rotation225 -> new Vector2d(-1, -1);
            case Rotation270 -> new Vector2d(-1, 0);
            case Rotation315 -> new Vector2d(-1, 1);
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case Rotation0 -> "0";
            case Rotation45 -> "45";
            case Rotation90 -> "90";
            case Rotation135 -> "135";
            case Rotation180 -> "180";
            case Rotation225 -> "225";
            case Rotation270 -> "270";
            case Rotation315 -> "315";
        };
    }

    public double rotationValue() {
        return switch (this) {
            case Rotation0 -> 0;
            case Rotation45 -> 45;
            case Rotation90 -> 90;
            case Rotation135 -> 135;
            case Rotation180 -> 180;
            case Rotation225 -> 225;
            case Rotation270 -> 270;
            case Rotation315 -> 315;
        };
    }
}
