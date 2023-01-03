package agh.ics.oop;

public class ForestedEquators extends Grassfield {

    public ForestedEquators(WorldParameters worldParams) {
        super(worldParams);
        setPercentsOfGrassChance();
        addGrassesToMap(worldParams.startQuantityOfGrass);
    }

    private void setPercentsOfGrassChance() {
        int fieldsRequired = (int) (Math.ceil((topRightCorner.x() - bottomLeftCorner.x()) * (topRightCorner.y() - bottomLeftCorner.y()) * 0.2));
        int howManyRows = 0;
        while (fieldsRequired > 0) {
            fieldsRequired -= (topRightCorner.x() - bottomLeftCorner.x());
            howManyRows += 1;
        }
        int startY = ((topRightCorner.y() - bottomLeftCorner.y()) - howManyRows) / 2;

        for (int y = bottomLeftCorner.y(); y <= topRightCorner.y(); y++) {
            for (int x = bottomLeftCorner.x(); x <= topRightCorner.x(); x++) {
                grassesMapPercent.put(new Vector2d(x, y), 20);
            }
        }
        for (int y = startY; y < startY + howManyRows; y++) {
            for (int x = bottomLeftCorner.x(); x <= topRightCorner.x(); x++) {
                grassesMapPercent.put(new Vector2d(x, y), 80);
            }
        }
    }
}
