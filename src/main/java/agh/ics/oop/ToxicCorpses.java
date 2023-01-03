package agh.ics.oop;

public class ToxicCorpses extends Grassfield implements DeathObserver {

    public ToxicCorpses(WorldParameters worldParams) {
        super(worldParams);
        setPercentsOfGrassChance();
    }

    private void setPercentsOfGrassChance() {
        for (int y = bottomLeftCorner.y(); y < topRightCorner.y(); y++) {
            for (int x = bottomLeftCorner.x(); x < topRightCorner.x(); x++) {
                grassesMapPercent.put(new Vector2d(x, y), 80);
            }
        }
    }

    @Override
    public void animalDied(Animal animal) {
        updatePercentsOfGrassChance(animal.getPosition());
    }

    private void updatePercentsOfGrassChance(Vector2d position) {
        grassesMapPercent.remove(position);
        grassesMapPercent.put(position, 20);
    }
}
