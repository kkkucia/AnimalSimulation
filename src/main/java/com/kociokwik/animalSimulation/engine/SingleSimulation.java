package com.kociokwik.animalSimulation.engine;

import com.kociokwik.animalSimulation.GUI.SimulationPage;
import javafx.application.Application;

public class SingleSimulation {
    SimulationEngine simulationEngine;

    public SingleSimulation(SimulationEngine simulationEngine) {
        this.simulationEngine = simulationEngine;
        //Application.launch(SimulationPage.class);
    }

    private static void day(SimulationEngine simulation) {
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
