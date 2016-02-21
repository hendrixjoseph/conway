package edu.wright.hendrix11.conway.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wright.hendrix11.conway.gui.grid.VisibleGrid;
import edu.wright.hendrix11.conway.gui.menu.BottomMenu;
import edu.wright.hendrix11.conway.gui.menu.TopMenu;
import edu.wright.hendrix11.conway.logic.Grid;
import edu.wright.hendrix11.conway.logic.Pattern;

public class Main extends Application {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

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

    private List<Pattern> loadPatterns() {
        URL resource = Main.class.getResource("/patterns");

        List<Pattern> patterns = new ArrayList<>();

        try {
            Path path = Paths.get(resource.toURI());
            Files.list(path).forEach(file -> {
                if(Files.isRegularFile(file) && file.getFileName().toString().endsWith("txt")) {
                    try {
                        List<String> pattern = Files.readAllLines(file);
                        String name = file.getFileName().toString().replace(".txt","");
                        patterns.add(new Pattern(name, pattern));
                    } catch (IOException e) {
                        LOG.log(Level.SEVERE, e.getClass().getName(), e);
                    }
                }
            });
        } catch (IOException | URISyntaxException e) {
            LOG.log(Level.SEVERE, e.getClass().getName(), e);
        }

        return patterns;
    }

    private VBox generateMainArea() {
        Grid gameGrid = new Grid();

        VBox group = new VBox();

        BottomMenu bottomMenu = new BottomMenu(gameGrid);
        bottomMenu.addPatterns(loadPatterns());
        VisibleGrid visibleGrid = new VisibleGrid(gameGrid, 30, bottomMenu::getInputType);

        group.getChildren().add(new TopMenu());
        group.getChildren().add(visibleGrid);
        group.getChildren().add(bottomMenu);

        return group;
    }
}
