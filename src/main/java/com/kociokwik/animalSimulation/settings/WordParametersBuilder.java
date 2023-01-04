package com.kociokwik.animalSimulation.settings;

import com.kociokwik.animalSimulation.map.GrassfieldType;
import com.kociokwik.animalSimulation.map.MapType;

public class WordParametersBuilder {
    public Integer width;
    public Integer height;
    public MapType mapType;
    public Integer startQuantityOfAnimals;
    public Integer startQuantityOfGrass;
    public Integer quantityGrassPerDay;
    public GrassfieldType grassfiledType;
    public Integer startEnergy;
    public Integer energyFullStomach;
    public Integer energyLostWhileProcreation;
    public Integer energyLossPerMove;
    public Integer energyFromGrass;

    public WorldParameters build() {
        if (width == null) {
            throw new WrongParameterException("width");
        }
        if (height == null) {
            throw new WrongParameterException("height");
        }
        if (mapType == null) {
            throw new WrongParameterException("mapType");
        }
        if (startQuantityOfAnimals == null) {
            throw new WrongParameterException("startQuantityOfAnimals");
        }
        if (startQuantityOfGrass == null) {
            throw new WrongParameterException("startQuantityOfGrass");
        }
        if (quantityGrassPerDay == null) {
            throw new WrongParameterException("quantityGrassPerDay");
        }
        if (grassfiledType == null) {
            throw new WrongParameterException("grassfiledType");
        }
        if (startEnergy == null) {
            throw new WrongParameterException("startEnergy");
        }
        if (energyFullStomach == null) {
            throw new WrongParameterException("energyFullStomach");
        }
        if (energyLostWhileProcreation == null) {
            throw new WrongParameterException("energyLostWhileProcreation");
        }
        if (energyFromGrass == null) {
            throw new WrongParameterException("energyFromGrass");
        }
        return new WorldParameters(width, height, mapType, startQuantityOfAnimals, startQuantityOfGrass, quantityGrassPerDay, grassfiledType, startEnergy, energyFullStomach, energyLostWhileProcreation, energyLossPerMove, energyFromGrass);
    }

    public WordParametersBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public WordParametersBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public WordParametersBuilder setMapType(MapType mapType) {
        this.mapType = mapType;
        return this;
    }

    public WordParametersBuilder setStartQuantityOfAnimals(int startQuantityOfAnimals) {
        this.startQuantityOfAnimals = startQuantityOfAnimals;
        return this;
    }

    public WordParametersBuilder setStartQuantityOfGrass(int startQuantityOfGrass) {
        this.startQuantityOfGrass = startQuantityOfGrass;
        return this;
    }

    public WordParametersBuilder setQuantityGrassPerDay(int quantityGrassPerDay) {
        this.quantityGrassPerDay = quantityGrassPerDay;
        return this;
    }

    public WordParametersBuilder setGrassfiledType(GrassfieldType grassfiledType) {
        this.grassfiledType = grassfiledType;
        return this;
    }

    public WordParametersBuilder setStartEnergy(int startEnergy) {
        this.startEnergy = startEnergy;
        return this;
    }

    public WordParametersBuilder setEnergyFullStomach(int energyFullStomach) {
        this.energyFullStomach = energyFullStomach;
        return this;
    }

    public WordParametersBuilder setEnergyLostWhileProcreation(int energyLostWhileProcreation) {
        this.energyLostWhileProcreation = energyLostWhileProcreation;
        return this;
    }

    public WordParametersBuilder setEnergyLossPerMove(int energyLossPerMove) {
        this.energyLossPerMove = energyLossPerMove;
        return this;
    }

    public WordParametersBuilder setEnergyFromGrass(int energyFromGrass) {
        this.energyFromGrass = energyFromGrass;
        return this;
    }
}
