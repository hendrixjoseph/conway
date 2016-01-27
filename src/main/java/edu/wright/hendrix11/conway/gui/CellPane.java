package edu.wright.hendrix11.conway.gui;

import javafx.scene.layout.Pane;

/**
 * @author Joe Hendrix
 */
public class CellPane extends Pane {
    public CellPane() {
        //setStyle("-fx-border-color: black;");
        setPrefSize(100.0f, 100.0f);

        this.setOnMouseClicked(e -> click());
    }

    private void click() {
        System.err.println("click!");
        setStyle("-fx-background-color: black; -fx-border-color: white;");
    }
}
