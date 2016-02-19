package edu.wright.hendrix11.conway.gui;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

import edu.wright.hendrix11.conway.logic.Grid;

/**
 * @author Joe Hendrix
 */
public class Bottom extends HBox {

    private Grid gameGrid;

    private double waitTime = 5.0;
    private Label sliderLabel = new Label();
    private Button startPauseButton = new Button("Start");
    private Button tickButton = new Button("Tick");

    public Bottom(Grid gameGrid) {
        super(10);

        this.gameGrid = gameGrid;

        ChoiceBox cb = new ChoiceBox();
        cb.getItems().addAll("item1", "item2", "item3");

        startPauseButton.setOnMouseClicked(action -> start());
        Button clearButton = new Button("Clear");
        clearButton.setOnMouseClicked(action -> gameGrid.clear());
        tickButton.setOnMouseClicked(action -> gameGrid.tick());

        setSpeed(waitTime);
        Slider speedSlider = new Slider(0, 10, waitTime);
        speedSlider.valueProperty().addListener(action -> setSpeed(speedSlider.getValue()));

        Label statusLabel = new Label("This is the status label.");

        this.getChildren().addAll(cb, tickButton, startPauseButton, clearButton, sliderLabel, speedSlider, statusLabel);
    }

    private void start() {
        startPauseButton.setText("Pause");
        startPauseButton.setOnMouseClicked(action -> pause());
        tickButton.setDisable(true);

        if(gameGrid.isAlive()) {
            gameGrid.restart();
        } else {
            gameGrid.start();
        }
    }

    private void pause() {
        startPauseButton.setText("Start");
        startPauseButton.setOnMouseClicked(action -> start());

        tickButton.setDisable(false);

        gameGrid.kill();
    }

    private void setSpeed(double speed) {
        this.waitTime = 10 - speed;
        sliderLabel.setText(String.format("Speed: %.2f", speed));
    }
}
