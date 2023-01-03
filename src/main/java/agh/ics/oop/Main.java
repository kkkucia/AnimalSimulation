package agh.ics.oop;


public class Main {
    public static void main(String[] args) throws Exception {
        WorldParameters worldParams = new WorldParameters(8, 8, MapType.EarthMap, 2, 5, 5, GrassfieldType.ForestedEquators, 20, 5, 2, 1, 1);
        GenomeParameters genomeParams = new GenomeParameters(10, 100, MutationType.Strict, 0, 0);

        SimulationEngine simulationTest = new SimulationEngine(worldParams, genomeParams);
        System.out.println("start");
        Thread.sleep(100);

        for (int i = 0; i < 10; i++) {
            day(simulationTest);
            System.out.println(simulationTest.getMap().toString());
            Thread.sleep(100);
        }
    }

    private static void day(SimulationEngine simulation) {
        simulation.removeDeadAnimals();
        simulation.rotateAndMoveAllAnimals();
        simulation.grassConsumption();
        simulation.animalsProcreation();
        simulation.dailyGrassGrowth();
        simulation.animalGetsOlder();
    }
}
