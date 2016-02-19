package edu.wright.hendrix11.conway.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import edu.wright.hendrix11.conway.logic.Grid;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        VBox group = new VBox();

        VisibleGrid visibleGrid = new VisibleGrid(30);

        group.getChildren().add(new ConwayMenu());
        group.getChildren().add(visibleGrid);
        group.getChildren().add(new Bottom(visibleGrid.getGameGrid()));

        Scene scene = new Scene(group, 700.0f, 700.0f);
        primaryStage.setTitle("CS7140 - Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}