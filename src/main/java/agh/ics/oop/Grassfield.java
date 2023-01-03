package agh.ics.oop;

import java.util.*;

abstract public class Grassfield implements WorldGrassfiled {

    protected final Vector2d topRightCorner;
    protected final Vector2d bottomLeftCorner;
    protected final WorldParameters worldParams;

    public Map<Vector2d, Grass> getGrassesOnMap() {
        return grassesOnMap;
    }

    protected Map<Vector2d, Grass> grassesOnMap = new HashMap<>();
    protected Map<Vector2d, Integer> grassesMapPercent = new HashMap<>();

    protected ArrayList<Vector2d> lotteryCoordinates = new ArrayList<>();

    public Grassfield(WorldParameters worldParams) {
        this.bottomLeftCorner = new Vector2d(0, 0);
        this.topRightCorner = new Vector2d(worldParams.width - 1, worldParams.height - 1);
        this.worldParams = worldParams;

        for (int y = 0; y <= topRightCorner.y(); y++) {
            for (int x = 0; x <= topRightCorner.x(); x++) {
                lotteryCoordinates.add(new Vector2d(x, y));
            }
        }
    }

    public void removeGrass(Vector2d position) {
        grassesOnMap.remove(position);
    }

    @Override
    public void addGrassesToMap(int quantityOfGrass) {
        List<Vector2d> currentGrassCoordinates = grassCoordinates(quantityOfGrass);
        for (Vector2d field : currentGrassCoordinates) {
            grassesOnMap.put(field, new Grass(field));
            lotteryCoordinates.remove(field);
        }
    }

    private List<Vector2d> grassCoordinates(int quantityOfGrass) {
        List<Vector2d> readyCoordinates = new LinkedList<>();
        int lotteryRange = findLotteryRange();
        int maxGrassQuantity = lotteryCoordinates.size();

        for (int i = 0; i < Math.min(quantityOfGrass, maxGrassQuantity); i++) {
            int random = new Random().nextInt(lotteryRange);
            int idx = findIdx(random);
            readyCoordinates.add(lotteryCoordinates.get(idx));
            lotteryRange -= grassesMapPercent.get(lotteryCoordinates.get(idx));
            lotteryCoordinates.remove(idx);
            maxGrassQuantity -= 1;
        }
        return readyCoordinates;
    }

    private int findIdx(int random) {
        for (int i = 0; i < lotteryCoordinates.size(); i++) {
            random -= grassesMapPercent.get(lotteryCoordinates.get(i));

            if (random <= 0) {
                return i;
            }
        }
        return lotteryCoordinates.size() - 1;
    }

    private int findLotteryRange() {
        int lotteryRange = 0;
        for (int i = 0; i < lotteryCoordinates.size(); i++) {
            lotteryRange += grassesMapPercent.get(lotteryCoordinates.get(i));
        }
        return lotteryRange;
    }

}

