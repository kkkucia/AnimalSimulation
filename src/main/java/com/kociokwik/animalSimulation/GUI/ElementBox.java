package com.kociokwik.animalSimulation.GUI;

import com.kociokwik.animalSimulation.map.element.Animal;
import com.kociokwik.animalSimulation.map.element.MapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ElementBox {
    private final VBox verticalBox;
    private final int fullEnergy;

    public ElementBox(MapElement element, int size, int fullEnergy) throws FileNotFoundException {
        verticalBox = new VBox();
        Image image = new Image(new FileInputStream(element.getPicture()));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(false);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        this.fullEnergy = fullEnergy;

        if (element.getZIndex() == 1) {
            Label label = new Label((element.getPosition().toString()));

            label.setStyle(lifeBarColor(((Animal) element).getEnergy()));
            verticalBox.getChildren().addAll(imageView, label);
            imageView.setRotate(((Animal) element).getCurrentRotation().rotationValue());
        } else {
            verticalBox.getChildren().add(imageView);
        }
        verticalBox.setAlignment(Pos.CENTER);
    }

    private String lifeBarColor(int energy) {
        int energyIndicator = energy * 100;
        String color = "-fx-text-fill: #ffffff; -fx-background-color:  #000000";
        if (energyIndicator >= 100 * fullEnergy) {
            color = "-fx-text-fill: #000000; -fx-background-color:  #60e10a";
        } else if (energyIndicator >= 75 * fullEnergy) {
            color = "-fx-text-fill: #000000; -fx-background-color:  #f2ff3c";
        } else if (energyIndicator >= 50 * fullEnergy) {
            color = "-fx-text-fill: #000000; -fx-background-color:  #a1ff3c";
        } else if (energyIndicator >= 25 * fullEnergy) {
            color = "-fx-text-fill: #000000; -fx-background-color:  #ff9e1d";
        } else if (energyIndicator >= fullEnergy) {
            color = "-fx-text-fill: #000000; -fx-background-color:  #ff044f";
        }
        return color;
    }

    public VBox getVerticalBox() {
        return verticalBox;
    }
}
