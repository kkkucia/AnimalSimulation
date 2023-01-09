package com.kociokwik.animalSimulation.engine;


public class SingleSimulation {
    SimulationEngine simulationEngine;

    public SingleSimulation(SimulationEngine simulationEngine) {
        this.simulationEngine = simulationEngine;
    }

    public void day(SimulationEngine simulation) {
        simulation.removeDeadAnimals();
        simulation.rotateAndMoveAllAnimals();
        simulation.grassConsumption();
        simulation.animalsProcreation();
        simulation.dailyGrassGrowth();
        simulation.animalGetsOlder();
    }

    public SimulationEngine getSimulationEngine() {
        return simulationEngine;
    }
}
