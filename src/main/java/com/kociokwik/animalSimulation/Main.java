package com.kociokwik.animalSimulation;

import com.kociokwik.animalSimulation.GUI.App;
import com.kociokwik.animalSimulation.engine.SimulationEngine;
import com.kociokwik.animalSimulation.map.GrassfieldType;
import com.kociokwik.animalSimulation.map.MapType;
import com.kociokwik.animalSimulation.map.element.genome.MutationType;
import com.kociokwik.animalSimulation.settings.GenomeParameters;
import com.kociokwik.animalSimulation.settings.GenomeParametersBuilder;
import com.kociokwik.animalSimulation.settings.WordParametersBuilder;
import com.kociokwik.animalSimulation.settings.WorldParameters;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) throws Exception {
        Application.launch(App.class, args);
    }

//    private static WorldParameters worldParams;
//    private static GenomeParameters genomeParams;

//    public static void main(String[] args) throws Exception {
//        setParams();
//        SimulationEngine simulation = new SimulationEngine(worldParams, genomeParams);
//
//        for(int i = 0; i <= 10; i++){
//            day(simulation);
//            System.out.println(simulation.getMap().toString());
//            System.out.println(simulation.getCountOfDeads());
//        }
//    }
//
//    private static void setParams() {
//        worldParams = new WordParametersBuilder()
//                .setWidth(8)
//                .setHeight(8)
//                .setMapType(MapType.EarthMap)
//                .setStartQuantityOfAnimals(2)
//                .setStartQuantityOfGrass(5)
//                .setQuantityGrassPerDay(5)
//                .setGrassfiledType(GrassfieldType.ForestedEquators)
//                .setStartEnergy(20)
//                .setEnergyFullStomach(5)
//                .setEnergyLostWhileProcreation(2)
//                .setEnergyLossPerMove(1)
//                .setEnergyFromGrass(1)
//                .build();
//
//        genomeParams = new GenomeParametersBuilder()
//                .setGenomeLength(10)
//                .setBehaviourPercent(100)
//                .setMutationType(MutationType.Strict)
//                .setMinPossibleMutationsNumber(0)
//                .setMaxPossibleMutationsNumber(0)
//                .build();
//    }
//
//    private static void day(SimulationEngine simulation) {
//        simulation.removeDeadAnimals();
//        simulation.rotateAndMoveAllAnimals();
//        simulation.grassConsumption();
//        simulation.animalsProcreation();
//        simulation.dailyGrassGrowth();
//        simulation.animalGetsOlder();
//    }
}
