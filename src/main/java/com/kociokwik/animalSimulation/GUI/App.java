package com.kociokwik.animalSimulation.GUI;

import com.kociokwik.animalSimulation.engine.SimulationEngine;
import com.kociokwik.animalSimulation.engine.SingleSimulation;
import com.kociokwik.animalSimulation.map.GrassfieldType;
import com.kociokwik.animalSimulation.map.MapType;
import com.kociokwik.animalSimulation.map.element.genome.MutationType;
import com.kociokwik.animalSimulation.settings.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    String css = this.getClass().getClassLoader().getResource("style.css").toExternalForm();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("welcomePage.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Animal Simulation");

        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
