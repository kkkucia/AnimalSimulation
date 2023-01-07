package com.kociokwik.animalSimulation.GUI;

import com.kociokwik.animalSimulation.engine.SimulationEngine;
import com.kociokwik.animalSimulation.engine.SingleSimulation;
import com.kociokwik.animalSimulation.map.Grassfield;
import com.kociokwik.animalSimulation.map.GrassfieldType;
import com.kociokwik.animalSimulation.map.MapType;
import com.kociokwik.animalSimulation.map.element.genome.MutationType;
import com.kociokwik.animalSimulation.settings.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class ConfigurationPage {
    String css = this.getClass().getClassLoader().getResource("style.css").toExternalForm();
    WorldParameters worldParameters;
    GenomeParameters genomeParameters;
    int behaviour = 100;
    MutationType mutationType = MutationType.Strict;
    GrassfieldType grassfieldType = GrassfieldType.ForestedEquators;
    MapType mapType = MapType.EarthMap;

    public void createAndStartSimulation(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        try {
            worldParameters = getWorldParameters(scene);
            genomeParameters = getGenomeParameters(scene);
            SimulationEngine engine = new SimulationEngine(worldParameters, genomeParameters);
            new SingleSimulation(engine);
            //createSimulationPage();
        } catch (
                WrongParameterException exception) {
                System.out.println(exception.getFieldName());
                TextField errorText = (TextField) scene.lookup("#error");
                errorText.setText("Wrong value of parameter: " + exception.getFieldName());
        }
    }

//    public void createSimulationPage() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("simulationPage.fxml"));
//        Parent root = loader.load();
//        Stage stage = new Stage();
//
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(css);
//
//        stage.setScene(scene);
//        stage.show();
//    }

    private WorldParameters getWorldParameters(Scene scene) {
        return new WordParametersBuilder()
                .setWidth(getIntegerFromField((TextField) scene.lookup("#width")))
                .setHeight(getIntegerFromField((TextField) scene.lookup("#height")))
                .setMapType(mapType)
                .setStartQuantityOfAnimals(getIntegerFromField((TextField) scene.lookup("#startQuantityOfAnimals")))
                .setStartQuantityOfGrass(getIntegerFromField((TextField) scene.lookup("#startQuantityOfGrass")))
                .setQuantityGrassPerDay(getIntegerFromField((TextField) scene.lookup("#quantityGrassPerDay")))
                .setGrassfiledType(grassfieldType)
                .setStartEnergy(getIntegerFromField((TextField) scene.lookup("#startEnergy")))
                .setEnergyFullStomach(getIntegerFromField((TextField) scene.lookup("#energyFullStomach")))
                .setEnergyLostWhileProcreation(getIntegerFromField((TextField) scene.lookup("#energyLostWhileProcreation")))
                .setEnergyLossPerMove(getIntegerFromField((TextField) scene.lookup("#energyLossPerMove")))
                .setEnergyFromGrass(getIntegerFromField((TextField) scene.lookup("#energyFromGrass")))
                .build();
    }

    private GenomeParameters getGenomeParameters(Scene scene) {
        return new GenomeParametersBuilder()
                .setGenomeLength(getIntegerFromField((TextField) scene.lookup("#genomeLength")))
                .setBehaviourPercent(behaviour)
                .setMutationType(mutationType)
                .setMinPossibleMutationsNumber(getIntegerFromField((TextField) scene.lookup("#minPossibleMutationsNumber")))
                .setMaxPossibleMutationsNumber(getIntegerFromField((TextField) scene.lookup("#maxPossibleMutationsNumber")))
                .build();
    }

    private Integer getIntegerFromField(TextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public void behaviourAction(ActionEvent action) {
        //System.out.println("Toggled: " + ((RadioButton)action.getSource()).getText());
        behaviour = ((RadioButton) action.getSource()).getText().equals("Crazy") ? 20 : 100;
    }

    public void mutationTypeAction(ActionEvent action) {
        mutationType = ((RadioButton) action.getSource()).getText().equals("Strict") ? MutationType.Strict : MutationType.Lottery;
    }

    public void mapTypeAction(ActionEvent action) {
        //System.out.println(((RadioButton)action.getSource()).getText().equals("Earth map"));
        mapType = ((RadioButton) action.getSource()).getText().equals("Earth map") ? MapType.EarthMap : MapType.HellMap;
    }

    public void grassfiledTypeAction(ActionEvent action) {
        grassfieldType = ((RadioButton) action.getSource()).getText().equals("Forested equators") ? GrassfieldType.ForestedEquators : GrassfieldType.ToxicCorpses;
    }

}
