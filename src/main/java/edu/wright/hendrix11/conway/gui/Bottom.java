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

    private VisibleGrid visibleGrid;

    public Bottom(VisibleGrid visibleGrid) {
        super(10);

        this.visibleGrid = visibleGrid;

        ChoiceBox cb = new ChoiceBox();
        cb.getItems().addAll("item1", "item2", "item3");

        Button start = new Button("Start");
        Button clear = new Button("Clear");
        Button tick = new Button("Tick");
        tick.setOnMouseClicked(event -> click());

        Slider speed = new Slider(0, 10, 0.5);

        Label statusLabel = new Label("This is the status label.");

        this.getChildren().addAll(cb, tick, start, clear, speed, statusLabel);
    }

    private void click() {
        visibleGrid.tick();
    }
}
