package com.kociokwik.animalSimulation.GUI;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SimulationPage extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("simulationPage.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Animal Simulation");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setX((( Screen.getPrimary().getVisualBounds().getWidth() - 600) / 2));
        primaryStage.setY(0);

        primaryStage.show();
    }

    public void printSmth(String text){

    }

}
