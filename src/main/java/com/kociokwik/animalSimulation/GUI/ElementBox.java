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

    public ElementBox(MapElement element, int width, int height) throws FileNotFoundException {
        verticalBox = new VBox();
        Image image = new Image(new FileInputStream(element.getPicture()));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(false);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        if (element instanceof Animal) {
            Label label = new Label((element.getPosition().toString()));
            verticalBox.getChildren().addAll(imageView, label);
            imageView.setRotate(((Animal) element).getCurrentRotation().rotationValue());
        }
        else{
            verticalBox.getChildren().add(imageView);
        }

        verticalBox.setAlignment(Pos.CENTER);
    }

    public VBox getVerticalBox() {
        return verticalBox;
    }
}
