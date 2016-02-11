package edu.wright.hendrix11.conway.gui;

import edu.wright.hendrix11.conway.logic.Grid;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class Main extends Application {

    private Grid gameGrid = new Grid();

    @Override
    public void start(Stage primaryStage) {

        VBox group = new VBox();

        VisibleGrid visibleGrid = new VisibleGrid(gameGrid, 100);

        group.getChildren().add(new ConwayMenu());
        group.getChildren().add(visibleGrid);
        group.getChildren().add(new Bottom(visibleGrid));

        Scene scene = new Scene(group, 700.0f, 700.0f);
        primaryStage.setTitle("CS7140 - Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}