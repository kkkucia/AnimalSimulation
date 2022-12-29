package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

abstract public class Grassfield implements WorldGrassfiled {

    protected final Vector2d topRightCorner;
    protected final Vector2d bottomLeftCorner;
    protected final WorldParameters worldParams;

    protected Map<Vector2d, Grass> grassesMapPercent = new HashMap<>();
    protected Map<Vector2d, Integer> getGrassesMapPercent = new HashMap<>();

    public Grassfield(WorldParameters worldParams) {
        this.bottomLeftCorner = new Vector2d(0, 0);
        this.topRightCorner = new Vector2d(worldParams.width, worldParams.height);
        this.worldParams = worldParams;

        addGrassesToMap();
    }

    private void addGrassesToMap() {
        for (int i = 0; i < worldParams.startQuantityOfGrass; i++) {
            Vector2d position = new Vector2d(new Random().nextInt(worldParams.width), new Random().nextInt(worldParams.height));
            Grass grass = new Grass(position);
            //place grass
        }
    }

}



