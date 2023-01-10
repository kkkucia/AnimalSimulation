package com.kociokwik.animalSimulation.GUI;

import com.kociokwik.animalSimulation.CsvWriter;
import com.kociokwik.animalSimulation.engine.SingleSimulation;
import com.kociokwik.animalSimulation.map.element.Animal;
import com.kociokwik.animalSimulation.map.element.Grass;
import com.kociokwik.animalSimulation.map.element.genome.Rotation;
import com.kociokwik.animalSimulation.settings.Statistics;
import com.kociokwik.animalSimulation.settings.Vector2d;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimulationPage implements Runnable {
    SingleSimulation simulation;
    Stage stage;
    private GridPane grid;
    final int IMG_SIZE = 32;
    final int SIZE = 52;

    private int i = 0;
    Timeline timeline = null;
    Animal animalToPrint = null;
    private List<VBox> domainGenomeAnimals = new LinkedList<VBox>();
    CsvWriter writer;

    public SimulationPage(SingleSimulation simulation, Stage stage) {
        this.simulation = simulation;
        this.stage = stage;
        if (simulation.getSimulationEngine().getWorldParams().wantCsv) {
            writer = new CsvWriter();
        }

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                if (timeline != null && timeline.getStatus() != Animation.Status.STOPPED) {
                    timeline.stop();
                    if (simulation.getSimulationEngine().getWorldParams().wantCsv) {
                        writer.saveFile();
                    }
                }
            }
        });
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

            timeline = new Timeline(new KeyFrame(Duration.seconds(simulation.getSimulationEngine().getWorldParams().dayDurance), event -> {
                i += 1;
                label.setText("Day " + i);
                simulation.day(simulation.getSimulationEngine());

                try {
                    setView();
                    setStatistics();
                    setAnimalStatsView();

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }));

            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            ((Button) stage.getScene().lookup("#pause")).setOnAction(event -> pauseAndUnpauseApp());
            ((Button) stage.getScene().lookup("#stop")).setOnAction(event -> stopSimulation());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setAnimalStatsView() {
        if (animalToPrint != null) {
            String age = animalToPrint.isAlive() ? ("Age: " + animalToPrint.getAge() + " days") : ("Age when died: " + animalToPrint.getAge() + " days");
            TextArea stats = (TextArea) stage.getScene().lookup("#animalStats");
            stats.setText("Position: " + animalToPrint.getPosition() + "\n" +
                    "Genome: " + animalToPrint.getGenome() + "\n" +
                    "Gene active: " + animalToPrint.getGenome().getCurrentGene() + "\n" +
                    "Energy: " + animalToPrint.getEnergy() + "\n" +
                    "Kids: " + animalToPrint.getKidsQuantity() + "\n" +
                    age + "\n" +
                    "Eaten grass: " + animalToPrint.getEatenGrassQuantity() + "\n");
        }
    }

    public void pauseAndUnpauseApp() {
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.pause();
            for (VBox element : domainGenomeAnimals) {
                element.setStyle("-fx-padding: 2 5 2 5;" +
                        "-fx-border-style: solid inside;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-insets: 5;" +
                        "-fx-border-radius: 5;" +
                        "-fx-border-color: #ff1b82;");
            }
            ((Button) stage.getScene().lookup("#pause")).setText("Play");
        } else if (timeline.getStatus() == Animation.Status.PAUSED) {
            timeline.play();
            ((Button) stage.getScene().lookup("#pause")).setText("Pause");
        }
    }

    public void stopSimulation() {
        timeline.stop();
        (stage.getScene().lookup("#pause")).setVisible(false);
        if (simulation.getSimulationEngine().getWorldParams().wantCsv) {
            writer.saveFile();
        }
    }

    private void setStatistics() {
        Statistics stats = new Statistics(
                i,
                simulation.getSimulationEngine().getQuantityOfAnimalsOnMap(),
                simulation.getSimulationEngine().getGrassfield().getGrassesOnMap().size(),
                simulation.getSimulationEngine().getCountOfDeads(),
                Math.round(simulation.getSimulationEngine().averageAnimalAgeWhenDied() * 100.0) / 100.0,
                Math.round(averageEnergyOfAnimals() * 100.0) / 100.0,
                Math.round(simulation.getSimulationEngine().averageQuantityKidsOfAnimals() * 100.0) / 100.0,
                numberOfFreeFields(),
                simulation.getSimulationEngine().dominateGene().toString());

        TextArea statsArea = (TextArea) stage.getScene().lookup("#stats");
        statsArea.setText("Quantity of animals: " + stats.animalsQuantity() + "\n" +
                "Quantity of plants: " + stats.grassQuantity() + "\n" +
                "Quantity of dead animals: " + stats.animalCorpsesQuantity() + "\n" +
                "Average age of animals: " + stats.averageAge() + "\n" +
                "Average energy of animals: " + stats.averageEnergy() + "\n" +
                "Average quantity kids of animals: " + stats.averageQuantityOfKids() + "\n" +
                "Quantity of free fields: " + stats.freeFields() + "\n" +
                "Dominant gene: " + stats.dominantGene());

        if (simulation.getSimulationEngine().getWorldParams().wantCsv) {
            writer.addDayToCsv(stats);
        }
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
        return simulation.getSimulationEngine().getQuantityOfAnimalsOnMap() == 0 ? 0 : sum / simulation.getSimulationEngine().getQuantityOfAnimalsOnMap();
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

        domainGenomeAnimals = new LinkedList<VBox>();

        for (List<Animal>[] tab : animalsOnMap) {
            for (List<Animal> animalsOnSingleFiled : tab) {
                if (animalsOnSingleFiled.size() > 0) {
                    Animal el = animalsOnSingleFiled.get(0);
                    VBox element = new ElementBox(el, IMG_SIZE, simulation.getSimulationEngine().getWorldParams().energyFullStomach).getVerticalBox();
                    Vector2d position = el.getPosition();
                    grid.add(element, position.x() - leftBottomCorner.x() + 1, rightTopCorner.y() - position.y() + 1);
                    GridPane.setHalignment(element, HPos.CENTER);
                    element.setOnMouseClicked((e) ->
                    {
                        animalClick(el);
                    });
                    checkGeneDomain(el, element);
                }
            }
        }
    }

    private void checkGeneDomain(Animal animal, VBox element) {
        for (Rotation gene : animal.getGenome().getSequence()) {
            if (gene.equals(simulation.getSimulationEngine().dominateGene())) {
                domainGenomeAnimals.add(element);
                break;
            }
        }
    }

    private void animalClick(Animal animal) {
        if (timeline.getStatus() == Animation.Status.PAUSED) {
            animalToPrint = animal;
            setAnimalStatsView();
        }
    }
}
