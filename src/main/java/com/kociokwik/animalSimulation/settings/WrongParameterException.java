package com.kociokwik.animalSimulation.settings;

public class WrongParameterException extends RuntimeException {
    private final String fieldName;

    public WrongParameterException(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
