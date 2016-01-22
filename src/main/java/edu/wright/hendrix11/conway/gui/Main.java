package edu.wright.hendrix11.conway.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class Main extends Application {

    private static final int rows = 100;
    private static final int columns = 100;

    private CellPane[][] cellPanes =  new CellPane[rows][columns];

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                pane.add(cellPanes[i][j] = new CellPane(), j, i);
            }
        }

        Scene scene = new Scene(pane, 700.0f, 700.0f);
        primaryStage.setTitle("CS7140 - Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}