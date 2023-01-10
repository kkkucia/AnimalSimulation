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
    public Float dayDurance;

    public boolean wantCsv;

    public WorldParameters build() {
        if (width == null || width <= 0) {
            throw new WrongParameterException("width");
        }
        if (height == null || height <= 0) {
            throw new WrongParameterException("height");
        }
        if (mapType == null) {
            throw new WrongParameterException("mapType");
        }
        if (startQuantityOfAnimals == null || startQuantityOfAnimals < 0) {
            throw new WrongParameterException("startQuantityOfAnimals");
        }
        if (startQuantityOfGrass == null || startQuantityOfGrass < 0) {
            throw new WrongParameterException("startQuantityOfGrass");
        }
        if (quantityGrassPerDay == null || quantityGrassPerDay < 0) {
            throw new WrongParameterException("quantityGrassPerDay");
        }
        if (grassfiledType == null) {
            throw new WrongParameterException("grassfiledType");
        }
        if (startEnergy == null || startEnergy < 0) {
            throw new WrongParameterException("startEnergy");
        }
        if (energyFullStomach == null || energyFullStomach < 0) {
            throw new WrongParameterException("Energy to full stomach");
        }
        if (energyLostWhileProcreation == null || energyLostWhileProcreation < 0) {
            throw new WrongParameterException("energyLostWhileProcreation");
        }
        if (energyFromGrass == null || energyFromGrass < 0) {
            throw new WrongParameterException("energyFromGrass");
        }
        if (dayDurance == null) {
            throw new WrongParameterException("day durance");
        }
        return new WorldParameters(width, height, mapType, startQuantityOfAnimals, startQuantityOfGrass, quantityGrassPerDay,
                grassfiledType, startEnergy, energyFullStomach, energyLostWhileProcreation, energyLossPerMove, energyFromGrass, dayDurance, wantCsv);
    }

    public WordParametersBuilder setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public WordParametersBuilder setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public WordParametersBuilder setMapType(MapType mapType) {
        this.mapType = mapType;
        return this;
    }

    public WordParametersBuilder setStartQuantityOfAnimals(Integer startQuantityOfAnimals) {
        this.startQuantityOfAnimals = startQuantityOfAnimals;
        return this;
    }

    public WordParametersBuilder setStartQuantityOfGrass(Integer startQuantityOfGrass) {
        this.startQuantityOfGrass = startQuantityOfGrass;
        return this;
    }

    public WordParametersBuilder setQuantityGrassPerDay(Integer quantityGrassPerDay) {
        this.quantityGrassPerDay = quantityGrassPerDay;
        return this;
    }

    public WordParametersBuilder setGrassfiledType(GrassfieldType grassfiledType) {
        this.grassfiledType = grassfiledType;
        return this;
    }

    public WordParametersBuilder setStartEnergy(Integer startEnergy) {
        this.startEnergy = startEnergy;
        return this;
    }

    public WordParametersBuilder setEnergyFullStomach(Integer energyFullStomach) {
        this.energyFullStomach = energyFullStomach;
        return this;
    }

    public WordParametersBuilder setEnergyLostWhileProcreation(Integer energyLostWhileProcreation) {
        this.energyLostWhileProcreation = energyLostWhileProcreation;
        return this;
    }

    public WordParametersBuilder setEnergyLossPerMove(Integer energyLossPerMove) {
        this.energyLossPerMove = energyLossPerMove;
        return this;
    }

    public WordParametersBuilder setEnergyFromGrass(Integer energyFromGrass) {
        this.energyFromGrass = energyFromGrass;
        return this;
    }

    public WordParametersBuilder setDayDurance(Float dayDurance) {
        this.dayDurance = dayDurance;
        return this;
    }

    public WordParametersBuilder setWantCsv(Boolean wantCsv) {
        this.wantCsv = wantCsv;
        return this;
    }
}
