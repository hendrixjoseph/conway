package edu.wright.hendrix11.conway.gui;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

/**
 * @author Joe Hendrix
 */
public class Bottom extends HBox {
    public Bottom() {
        super(10);

        ChoiceBox cb = new ChoiceBox();
        cb.getItems().addAll("item1", "item2", "item3");

        Button next = new Button("Next");
        Button start = new Button("Start");
        Button clear = new Button("Clear");

        Slider speed = new Slider(0, 10, 0.5);

        Label statusLabel = new Label("This is the status label.");

        this.getChildren().addAll(cb,next,start,clear,speed,statusLabel);
    }
}
