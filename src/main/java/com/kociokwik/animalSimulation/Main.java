package com.kociokwik.animalSimulation;

import com.kociokwik.animalSimulation.GUI.App;
import com.kociokwik.animalSimulation.engine.SimulationEngine;
import com.kociokwik.animalSimulation.map.GrassfieldType;
import com.kociokwik.animalSimulation.map.MapType;
import com.kociokwik.animalSimulation.map.element.genome.MutationType;
import com.kociokwik.animalSimulation.settings.*;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) throws Exception {
        Application.launch(App.class, args);
    }
}
