package com.kociokwik.animalSimulation.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class WelcomePage {

    public void startSettingParams(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("configurationPage.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Configuration params");
        primaryStage.setScene(scene);
        primaryStage.show();

        makeFilesDropdown(scene);
    }

    private void makeFilesDropdown(Scene scene) {
        File folder = new File("./src/main/resources/configures/");
        File[] listOfFiles = folder.listFiles();

        ObservableList<String> options =
                FXCollections.observableArrayList();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                options.add(file.getName());
            }
        }

        ((ComboBox) scene.lookup("#fileToOpen")).setItems(options);
    }
}
