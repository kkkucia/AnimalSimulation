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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;


public class ConfigurationPage {
    WorldParameters worldParameters;
    GenomeParameters genomeParameters;
    GrassfieldType grassfieldType = GrassfieldType.ForestedEquators;
    MutationType mutationType = MutationType.Strict;
    MapType mapType = MapType.EarthMap;
    Boolean wantCsv = false;
    int behaviour = 100;
    private final int STAGE_WIDTH = 1500;
    private final int STAGE_HEIGHT = 750;

    public void createAndStartSimulation(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        try {
            worldParameters = getWorldParameters(scene);
            genomeParameters = getGenomeParameters(scene);
            SimulationEngine engine = new SimulationEngine(worldParameters, genomeParameters);
            createSimulationPage(new SingleSimulation(engine));
        } catch (WrongParameterException exception) {
            System.out.println(((ComboBox) scene.lookup("#fileToOpen")).getValue());
            Object fileName = ((ComboBox) scene.lookup("#fileToOpen")).getValue();
            if (fileName != null) {
                getDataFromFile(fileName.toString(), scene);
            } else {
                TextField errorText = (TextField) scene.lookup("#error");
                errorText.setText("Wrong value of parameter: " + exception.getFieldName());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getDataFromFile(String fileName, Scene scene) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("./src/main/resources/configures/" + fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Path path = Paths.get("./src/main/resources/configures/" + fileName);

        long numLines = 0;
        try {
            numLines = Files.lines(path).count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, String> values = new HashMap<>();

        for (int idx = 0; idx < numLines; idx++) {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            line = line.trim();
            System.out.println(line);
            String[] words = line.split(":");
            if (words.length == 2) {
                System.out.println("nazwa parametru: " + words[0]);
                System.out.println("wartość: " + words[1]);
                values.put(words[0].trim(), words[1].trim());
            }
        }

        try {
            worldParameters = getWorldParametersFromFile(values);
            genomeParameters = getGenomeParametersFromFile(values);
            SimulationEngine engine = new SimulationEngine(worldParameters, genomeParameters);
            createSimulationPage(new SingleSimulation(engine));
        } catch (WrongParameterException exception) {
            TextField errorText = (TextField) scene.lookup("#error");
            errorText.setText("Wrong value of parameter in file " + exception.getFieldName());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private WorldParameters getWorldParametersFromFile(HashMap<String, String> params) {
        return new WordParametersBuilder()
                .setWidth(getIntegerFromField(params.get("width")))
                .setHeight(getIntegerFromField(params.get("height")))
                .setMapType((params.containsKey("mapType") && params.get("mapType").equals("Earth map")) ? MapType.EarthMap : MapType.HellMap)
                .setStartQuantityOfAnimals(getIntegerFromField(params.get("startQuantityOfAnimals")))
                .setStartQuantityOfGrass(getIntegerFromField(params.get("startQuantityOfGrass")))
                .setQuantityGrassPerDay(getIntegerFromField(params.get("quantityGrassPerDay")))
                .setGrassfiledType((params.containsKey("grassfieldType") && params.get("grassfiledType").equals("Forested equators")) ? GrassfieldType.ForestedEquators : GrassfieldType.ToxicCorpses)
                .setStartEnergy(getIntegerFromField(params.get("startEnergy")))
                .setEnergyFullStomach(getIntegerFromField(params.get("energyFullStomach")))
                .setEnergyLostWhileProcreation(getIntegerFromField(params.get("energyLostWhileProcreation")))
                .setEnergyLossPerMove(getIntegerFromField(params.get("energyLossPerMove")))
                .setEnergyFromGrass(getIntegerFromField(params.get("energyFromGrass")))
                .setDayDurance(getFloatFromField(params.get("dayDurance")))
                .setWantCsv(params.containsKey("wantCsv") && params.get("wantCsv").equals("Yes"))
                .build();
    }

    private GenomeParameters getGenomeParametersFromFile(HashMap<String, String> params) {
        return new GenomeParametersBuilder()
                .setGenomeLength(getIntegerFromField(params.get("genomeLength")))
                .setBehaviourPercent(getIntegerFromField(params.get("behaviourPercent")))
                .setMutationType(params.containsKey("mutationType") && params.get("mutationType").equals("strict") ? MutationType.Strict : MutationType.Lottery)
                .setMinPossibleMutationsNumber(getIntegerFromField(params.get("minPossibleMutationsNumber")))
                .setMaxPossibleMutationsNumber(getIntegerFromField(params.get("maxPossibleMutationsNumber")))
                .build();
    }

    public void createSimulationPage(SingleSimulation simulation) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("simulationPage.fxml"));
        Stage stage = new Stage();
        SimulationPage controller = new SimulationPage(simulation, stage);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        String simulationCss = this.getClass().getClassLoader().getResource("styles/styleSimulation.css").toExternalForm();
        scene.getStylesheets().add(simulationCss);
        stage.setWidth(STAGE_WIDTH);
        stage.setHeight(STAGE_HEIGHT);
        stage.setScene(scene);
        stage.show();
        controller.run();
    }

    private WorldParameters getWorldParameters(Scene scene) {
        return new WordParametersBuilder()
                .setWidth(getIntegerFromField(((TextField) scene.lookup("#width")).getText()))
                .setHeight(getIntegerFromField(((TextField) scene.lookup("#height")).getText()))
                .setMapType(mapType)
                .setStartQuantityOfAnimals(getIntegerFromField(((TextField) scene.lookup("#startQuantityOfAnimals")).getText()))
                .setStartQuantityOfGrass(getIntegerFromField(((TextField) scene.lookup("#startQuantityOfGrass")).getText()))
                .setQuantityGrassPerDay(getIntegerFromField(((TextField) scene.lookup("#quantityGrassPerDay")).getText()))
                .setGrassfiledType(grassfieldType)
                .setStartEnergy(getIntegerFromField(((TextField) scene.lookup("#startEnergy")).getText()))
                .setEnergyFullStomach(getIntegerFromField(((TextField) scene.lookup("#energyFullStomach")).getText()))
                .setEnergyLostWhileProcreation(getIntegerFromField(((TextField) scene.lookup("#energyLostWhileProcreation")).getText()))
                .setEnergyLossPerMove(getIntegerFromField(((TextField) scene.lookup("#energyLossPerMove")).getText()))
                .setEnergyFromGrass(getIntegerFromField(((TextField) scene.lookup("#energyFromGrass")).getText()))
                .setDayDurance(getFloatFromField(((TextField) scene.lookup("#dayDurance")).getText()))
                .setWantCsv(wantCsv)
                .build();
    }

    private GenomeParameters getGenomeParameters(Scene scene) {
        return new GenomeParametersBuilder()
                .setGenomeLength(getIntegerFromField(((TextField) scene.lookup("#genomeLength")).getText()))
                .setBehaviourPercent(behaviour)
                .setMutationType(mutationType)
                .setMinPossibleMutationsNumber(getIntegerFromField(((TextField) scene.lookup("#minPossibleMutationsNumber")).getText()))
                .setMaxPossibleMutationsNumber(getIntegerFromField(((TextField) scene.lookup("#maxPossibleMutationsNumber")).getText()))
                .build();
    }

    private Float getFloatFromField(String text) {
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private Integer getIntegerFromField(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public void behaviourAction(ActionEvent action) {
        behaviour = ((RadioButton) action.getSource()).getText().equals("Crazy") ? 20 : 100;
    }

    public void mutationTypeAction(ActionEvent action) {
        mutationType = ((RadioButton) action.getSource()).getText().equals("Strict") ? MutationType.Strict : MutationType.Lottery;
    }

    public void mapTypeAction(ActionEvent action) {
        mapType = ((RadioButton) action.getSource()).getText().equals("Earth map") ? MapType.EarthMap : MapType.HellMap;
    }

    public void grassfiledTypeAction(ActionEvent action) {
        grassfieldType = ((RadioButton) action.getSource()).getText().equals("Forested equators") ? GrassfieldType.ForestedEquators : GrassfieldType.ToxicCorpses;
    }

    public void wantCsvAction(ActionEvent action) {
        wantCsv = ((RadioButton) action.getSource()).getText().equals("Yes, I want");
    }
}
