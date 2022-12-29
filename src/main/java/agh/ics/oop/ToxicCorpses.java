package agh.ics.oop;

public class ToxicCorpses extends Grassfield implements DeathObserver{

    public ToxicCorpses(WorldParameters worldParams) {
        super(worldParams);
    }

    @Override
    public void animalDied(Animal animal) {

    }
}
