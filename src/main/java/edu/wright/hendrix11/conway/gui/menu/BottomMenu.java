package edu.wright.hendrix11.conway.gui.menu;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wright.hendrix11.conway.logic.Grid;
import edu.wright.hendrix11.conway.logic.Pattern;

/**
 * @author Joe Hendrix
 */
public class BottomMenu extends HBox {

    private static final Logger LOG = Logger.getLogger(BottomMenu.class.getName());
    private Button clearButton = new Button("Clear");
    private Grid gameGrid;
    private ChoiceBox<Pattern> inputChoiceBox = new ChoiceBox<>();
    private volatile boolean running = false;
    private Label sliderLabel = new Label();
    private Button startPauseButton = new Button("Start");
    private Label statusLabel = new Label("Generation: 0");
    private Button tickButton = new Button("Tick");
    private long waitTime = 500;

    public BottomMenu(Grid gameGrid) {
        super(10);
        getStyleClass().add("bottom");

        this.gameGrid = gameGrid;

        inputChoiceBox.getItems().addAll(Pattern.single, Pattern.block);
        inputChoiceBox.setValue(Pattern.single);

        startPauseButton.setOnMouseClicked(action -> start());
        clearButton.setOnMouseClicked(action -> gameGrid.clear());
        tickButton.setOnMouseClicked(action -> tick());

        setSpeed(waitTime / 100.0);
        Slider speedSlider = new Slider(0.5, 10, waitTime / 100);
        speedSlider.valueProperty().addListener(action -> setSpeed(speedSlider.getValue()));

        this.getChildren().addAll(inputChoiceBox, tickButton, startPauseButton, clearButton, sliderLabel,
                speedSlider, statusLabel);
    }

    /**
     * @param pattern
     */
    public void addPattern(Pattern pattern) {
        inputChoiceBox.getItems().add(pattern);
    }

    /**
     * @param patterns
     */
    public void addPatterns(List<Pattern> patterns) {
        inputChoiceBox.getItems().addAll(patterns);
    }

    /**
     * @return
     */
    public Pattern getPattern() {
        return inputChoiceBox.getValue();
    }

    /**
     *
     */
    private void pause() {
        startPauseButton.setText("Start");
        startPauseButton.setOnMouseClicked(action -> start());

        tickButton.setDisable(false);

        running = false;
    }

    /**
     * @param speed
     */
    private void setSpeed(double speed) {
        waitTime = 1050 - (long) (speed * 100);
        sliderLabel.setText(String.format("Speed: %.2f", speed));
    }

    /**
     *
     */
    private void start() {
        startPauseButton.setText("Pause");
        startPauseButton.setOnMouseClicked(action -> pause());
        tickButton.setDisable(true);
        running = true;

        new Thread() {
            @Override
            public void run() {
                while (running) {
                    try {
                        gameGrid.tick();
                        Thread.sleep(waitTime);
                    } catch (InterruptedException e) {
                        LOG.log(Level.SEVERE, e.getClass().getName(), e);
                    }
                }
            }
        }.start();
    }

    /**
     *
     */
    private void tick() {
        gameGrid.tick();
        updateStatusLabel();
    }

    /**
     *
     */
    private void updateStatusLabel() {
        statusLabel.setText("Generation: " + gameGrid.getGeneration());
    }

}
