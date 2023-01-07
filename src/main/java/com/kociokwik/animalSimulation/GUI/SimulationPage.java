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
import javafx.scene.control.Label;
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
    final int width = 30;
    final int height = 30;
    private int i = 0;

    public SimulationPage(SingleSimulation simulation, Stage stage) {
        this.simulation = simulation;
        this.stage = stage;

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
            label.setText("Day 0");

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                i += 1;
                label.setText("Day " + i);
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

//            for(int j = 0; j < 10; j++){
//                PauseTransition pause = new PauseTransition(Duration.seconds(2));
//                System.out.println("before" + i);
//                pause.setOnFinished(event ->{
//                    System.out.println(i);
//                    label.setText("Value "+i++);
//                    if (i<=6) {
//                        pause.play();
//                    } else {
//                        label.setText("LAST TIME");
//                    }
//                });
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

        for (int i = leftBottomCorner.x(); i <= rightTopCorner.x(); i++) {
            Label labelX = new Label("" + i);
            grid.add(labelX, i - leftBottomCorner.x() + 1, 0);
            grid.getColumnConstraints().add(new ColumnConstraints(width));
            GridPane.setHalignment(labelX, HPos.CENTER);
        }

        for (int i = leftBottomCorner.y(); i <= rightTopCorner.y(); i++) {
            Label labelY = new Label("" + i);
            grid.add(labelY, 0, rightTopCorner.y() - i + 1);
            grid.getRowConstraints().add(new RowConstraints(height));
            GridPane.setHalignment(labelY, HPos.CENTER);
        }
        for (Grass el : grassesOnMap.values()) {
            VBox element = new ElementBox(el, width, height).getVerticalBox();
            Vector2d position = el.getPosition();
            grid.add(element, position.x() - leftBottomCorner.x() + 1, rightTopCorner.y() - position.y() + 1);
            GridPane.setHalignment(element, HPos.CENTER);
        }

        for (List<Animal>[] tab : animalsOnMap) {
            for (List<Animal> animalsOnSingleFiled : tab) {
                if (animalsOnSingleFiled.size() > 0) {
                    Animal el = animalsOnSingleFiled.get(0);
                    VBox element = new ElementBox(el, width, height).getVerticalBox();
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
