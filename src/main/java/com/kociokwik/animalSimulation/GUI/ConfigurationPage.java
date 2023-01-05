package com.kociokwik.animalSimulation.GUI;

import com.kociokwik.animalSimulation.engine.SimulationEngine;
import com.kociokwik.animalSimulation.engine.SingleSimulation;
import com.kociokwik.animalSimulation.map.GrassfieldType;
import com.kociokwik.animalSimulation.map.MapType;
import com.kociokwik.animalSimulation.map.element.genome.MutationType;
import com.kociokwik.animalSimulation.settings.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;


public class ConfigurationPage {
    String css = this.getClass().getClassLoader().getResource("style.css").toExternalForm();

    public void createAndStartSimulation(ActionEvent event) {
        try {
            Scene scene = ((Node)event.getSource()).getScene();
            WorldParameters worldParameters = getWorldParameters(scene);
            GenomeParameters genomeParameters = getGenomeParameters(scene);
            SimulationEngine engine = new SimulationEngine(worldParameters, genomeParameters);
            //SingleSimulation simulation = new SingleSimulation(engine);
            createSimulationPage();
        } catch (
                WrongParameterException exception) {
            System.out.println(exception.getFieldName());
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createSimulationPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("welcomePage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    private WorldParameters getWorldParameters(Scene scene) {
//        RadioButton hell = (RadioButton) scene.lookup("#hellMap");
//        RadioButton earth = (RadioButton) scene.lookup("#earthMap");
//        ToggleGroup mapType = new ToggleGroup();
//        hell.setToggleGroup(mapType)
//        earth.setToggleGroup(mapType);
//        mapType.selectToggle(earth);

        return new WordParametersBuilder()
                .setWidth(getIntegerFromField((TextField) scene.lookup("#width")))
                .setHeight(getIntegerFromField((TextField) scene.lookup("#height")))
                .setMapType(MapType.HellMap)
                .setStartQuantityOfAnimals(getIntegerFromField((TextField) scene.lookup("#startQuantityOfAnimals")))
                .setStartQuantityOfGrass(getIntegerFromField((TextField) scene.lookup("#startQuantityOfGrass")))
                .setQuantityGrassPerDay(getIntegerFromField((TextField) scene.lookup("#quantityGrassPerDay")))
                .setGrassfiledType(GrassfieldType.ForestedEquators)
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
                .setBehaviourPercent(80)
                .setMutationType(MutationType.Strict)
                .setMinPossibleMutationsNumber(getIntegerFromField((TextField) scene.lookup("#minPossibleMutationsNumber")))
                .setMaxPossibleMutationsNumber(getIntegerFromField((TextField) scene.lookup("#maxPossibleMutationsNumber")))
                .build();
    }

    private int getIntegerFromField(TextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException exception) {
            System.out.println("Nie wolno tak!!!!");
            System.exit(0);
        }
        return 0;
    }

    private MapType getTypeFromField(ToggleGroup group) {
        if (group.getSelectedToggle() != null) {
            //String data = group.getSelectedToggle().getUserData().toString().toLowerCase().trim();
            String data = group.getSelectedToggle().getUserData().toString();
            System.out.println(data);
        }
        return MapType.EarthMap;
    }
}
