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
        Scene scene = new Scene(generateMainArea(), 700.0f, 700.0f);
        scene.getStylesheets().add("style.css");

        primaryStage.setTitle("CS7140 - Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox generateMainArea() {
        Grid gameGrid = new Grid();

        VBox group = new VBox();

        BottomMenu bottomMenu = new BottomMenu(gameGrid);
        VisibleGrid visibleGrid = new VisibleGrid(gameGrid, 30, bottomMenu::getInputType);

        group.getChildren().add(new TopMenu());
        group.getChildren().add(visibleGrid);
        group.getChildren().add(bottomMenu);

        return group;
    }
}
