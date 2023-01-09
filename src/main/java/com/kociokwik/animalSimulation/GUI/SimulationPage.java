package com.kociokwik.animalSimulation.GUI;

import com.kociokwik.animalSimulation.engine.SimulationEngine;
import com.kociokwik.animalSimulation.engine.SingleSimulation;
import com.kociokwik.animalSimulation.map.element.Animal;
import com.kociokwik.animalSimulation.map.element.Grass;
import com.kociokwik.animalSimulation.settings.Vector2d;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class SimulationPage implements Runnable {
    SingleSimulation simulation;
    Stage stage;
    private GridPane grid;
    final int IMG_SIZE;
    final int SIZE;

    private int i = 0;
    Timeline timeline;

    public SimulationPage(SingleSimulation simulation, Stage stage) {
        this.simulation = simulation;
        this.stage = stage;
        IMG_SIZE = 32;
        SIZE = 52;
        System.out.println(simulation.getSimulationEngine().getMap().toString());
    }

    @Override
    public void run() {
        try {
            grid = (GridPane) stage.getScene().lookup("#grid");

            while (grid.getRowConstraints().size() > 0) {
                grid.getRowConstraints().remove(0);
            }

            while (grid.getColumnConstraints().size() > 0) {
                grid.getColumnConstraints().remove(0);
            }

            Label label = (Label) stage.getScene().lookup("#test");
            setView();
            setStatistics();
            label.setText("Day 0");

            timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                i += 1;
                label.setText("Day " + i);
                day(simulation.getSimulationEngine());

                try {
                    setView();
                    setStatistics();

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            ((Button) stage.getScene().lookup("#resume")).setOnAction(event -> unpauseApp());
            ((Button) stage.getScene().lookup("#pause")).setOnAction(event -> pauseApp());
            ((Button) stage.getScene().lookup("#stop")).setOnAction(event -> stopSimulation());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void pauseApp() {
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.pause();
        }
    }

    public void unpauseApp() {
        if (timeline.getStatus() == Animation.Status.PAUSED) {
            timeline.play();
        }
    }

    public void stopSimulation() {
        timeline.stop();
        (stage.getScene().lookup("#resume")).setVisible(false);
        (stage.getScene().lookup("#pause")).setVisible(false);
    }

//    private void setStatistics() {
////        najpopularniejszych genotypów,
////        średniej liczby dzieci dla żyjących zwierząt (wartość uwzględnia wszystkie powstałe zwierzęta, a nie tylko zwierzęta powstałe w danej epoce).
//        TextArea stats = (TextArea) stage.getScene().lookup("#stats");
//        stats.setText("Quantity of animals: " + simulation.getSimulationEngine().getMap().getQuantityOfAnimalsOnMap() + "\n" +
//                "Quantity of plants: " + simulation.getSimulationEngine().getGrassfield().getGrassesOnMap().size() + "\n" +
//                "Quantity of dead animals: " + simulation.getSimulationEngine().getCountOfDeads() + "\n" +
//                "Average age of animals: " + simulation.getSimulationEngine().getAverageAnimalAgeWhenDied() + "\n" +
//                "Average energy of animals: " + averageEnergyOfAnimals() + "\n" +
//                "Quantity of free fields: " + numberOfFreeFields());
//    }

    private void setStatistics() {
//        najpopularniejszych genotypów,
//        średniej liczby dzieci dla żyjących zwierząt (wartość uwzględnia wszystkie powstałe zwierzęta, a nie tylko zwierzęta powstałe w danej epoce).
        TextArea stats = (TextArea) stage.getScene().lookup("#stats");
        stats.setText("Quantity of animals: " + simulation.getSimulationEngine().getQuantityOfAnimalsOnMap() + "\n" +
                "Quantity of plants: " + simulation.getSimulationEngine().getGrassfield().getGrassesOnMap().size() + "\n" +
                "Quantity of dead animals: " + simulation.getSimulationEngine().getCountOfDeads() + "\n" +
                "Average age of animals: " + simulation.getSimulationEngine().getAverageAnimalAgeWhenDied() + "\n" +
                "Average energy of animals: " + averageEnergyOfAnimals() + "\n" +
                "Quantity of free fields: " + numberOfFreeFields());
    }

    private double averageEnergyOfAnimals() {
        double sum = 0;

        for (List<Animal>[] tab : simulation.getSimulationEngine().getMap().getAnimalsOnMap()) {
            for (List<Animal> animalsOnSingleFiled : tab) {
                for (Animal animal : animalsOnSingleFiled) {
                    sum += animal.getEnergy();
                }
            }
        }

        return sum / simulation.getSimulationEngine().getQuantityOfAnimalsOnMap();
    }

    private int numberOfFreeFields() {
        int num = 0;

        for (int y = 0; y <= simulation.getSimulationEngine().getMap().getTopRightCorner().y(); y++) {
            for (int x = 0; x <= simulation.getSimulationEngine().getMap().getTopRightCorner().x(); x++) {
                Vector2d position = new Vector2d(x, y);

                if (!simulation.getSimulationEngine().getGrassfield().getGrassesOnMap().containsKey(position) && simulation.getSimulationEngine().getMap().getAnimalsOnMap()[y][x].size() == 0) {
                    num += 1;
                }
            }
        }

        return num;
    }

    private void setView() throws FileNotFoundException {
        grid.setGridLinesVisible(false);
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        grid.setGridLinesVisible(true);

        List<Animal>[][] animalsOnMap = simulation.getSimulationEngine().getMap().getAnimalsOnMap();
        Map<Vector2d, Grass> grassesOnMap = simulation.getSimulationEngine().getGrassfield().getGrassesOnMap();

        Vector2d leftBottomCorner = simulation.getSimulationEngine().getMap().getBottomLeftCorner();
        Vector2d rightTopCorner = simulation.getSimulationEngine().getMap().getTopRightCorner();
        Label label = new Label("y / x");
        grid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
        grid.getColumnConstraints().add(new ColumnConstraints(IMG_SIZE));
        grid.getRowConstraints().add(new RowConstraints(IMG_SIZE));


        for (int i = leftBottomCorner.x(); i <= rightTopCorner.x(); i++) {
            Label labelX = new Label("" + i);
            grid.add(labelX, i - leftBottomCorner.x() + 1, 0);
            grid.getColumnConstraints().add(new ColumnConstraints(SIZE));
            GridPane.setHalignment(labelX, HPos.CENTER);
        }

        for (int i = leftBottomCorner.y(); i <= rightTopCorner.y(); i++) {
            Label labelY = new Label("" + i);
            grid.add(labelY, 0, rightTopCorner.y() - i + 1);
            grid.getRowConstraints().add(new RowConstraints(SIZE));
            GridPane.setHalignment(labelY, HPos.CENTER);
        }
        for (Grass el : grassesOnMap.values()) {
            VBox element = new ElementBox(el, IMG_SIZE, simulation.getSimulationEngine().getWorldParams().energyFullStomach).getVerticalBox();
            Vector2d position = el.getPosition();
            grid.add(element, position.x() - leftBottomCorner.x() + 1, rightTopCorner.y() - position.y() + 1);
            GridPane.setHalignment(element, HPos.CENTER);
        }

        for (List<Animal>[] tab : animalsOnMap) {
            for (List<Animal> animalsOnSingleFiled : tab) {
                if (animalsOnSingleFiled.size() > 0) {
                    Animal el = animalsOnSingleFiled.get(0);
                    VBox element = new ElementBox(el, IMG_SIZE, simulation.getSimulationEngine().getWorldParams().energyFullStomach).getVerticalBox();
                    Vector2d position = el.getPosition();
                    grid.add(element, position.x() - leftBottomCorner.x() + 1, rightTopCorner.y() - position.y() + 1);
                    GridPane.setHalignment(element, HPos.CENTER);
                }
            }
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
