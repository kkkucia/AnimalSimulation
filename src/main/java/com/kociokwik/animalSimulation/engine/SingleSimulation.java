package com.kociokwik.animalSimulation.engine;

import com.kociokwik.animalSimulation.GUI.SimulationPage;
import javafx.application.Application;

public class SingleSimulation {
    SimulationEngine simulationEngine;

    public SingleSimulation(SimulationEngine simulationEngine) {
        this.simulationEngine = simulationEngine;
        Application.launch(SimulationPage.class);

        for (int i = 0; i < 10; i++){

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
