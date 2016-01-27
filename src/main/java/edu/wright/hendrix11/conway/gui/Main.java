package edu.wright.hendrix11.conway.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class Main extends Application {

    private static final int rows = 100;
    private static final int columns = 100;

    private CellPane[][] cellPanes =  new CellPane[rows][columns];

    @Override
    public void start(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        fileMenu.getItems().add(new MenuItem("New"));
        fileMenu.getItems().add(new MenuItem("Open"));
        fileMenu.getItems().add(new MenuItem("Save"));
        fileMenu.getItems().add(new MenuItem("Save As"));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Exit"));
        menuBar.getMenus().add(fileMenu);

        Label statusLabel = new Label("This is the status label.");

        GridPane pane = new GridPane();

        VBox group = new VBox();
        group.getChildren().add(menuBar);
        group.getChildren().add(pane);
        group.getChildren().add(statusLabel);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                pane.add(cellPanes[i][j] = new CellPane(), j, i);
            }
        }

        Scene scene = new Scene(group, 700.0f, 700.0f);
        primaryStage.setTitle("CS7140 - Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}