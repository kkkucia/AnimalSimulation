package agh.ics.oop;

public class WorldParameters {
    public int width;
    public int height;
    public MapType mapType;
    public int startQuantityOfAnimals;
    public int startQuantityOfGrass;
    public int quantityGrassPerDay;
    public GrassfieldType grassfiledType;

    public int startEnergy;
    public int energyFullStomach;
    public int energyLostWhileProcreation;
    public int energyLossPerMove;
    public int energyFromGrass;

    public WorldParameters(int width, int height, MapType mapType, int startQuantityOfAnimals, int startQuantityOfGrass, int quantityGrassPerDay, GrassfieldType grassfiledType, int startEnergy, int energyFullStomach, int energyLostWhileProcreation, int energyLossPerMove, int energyFromGrass) {
        this.width = width;
        this.height = height;
        this.mapType = mapType;
        this.startQuantityOfAnimals = startQuantityOfAnimals;
        this.startQuantityOfGrass = startQuantityOfGrass;
        this.quantityGrassPerDay = quantityGrassPerDay;
        this.grassfiledType = grassfiledType;
        this.startEnergy = startEnergy;
        this.energyFullStomach = energyFullStomach;
        this.energyLostWhileProcreation = energyLostWhileProcreation;
        this.energyLossPerMove = energyLossPerMove;
        this.energyFromGrass = energyFromGrass;
    }
}
