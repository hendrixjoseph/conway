package edu.wright.hendrix11.conway.gui.menu;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wright.hendrix11.conway.logic.Pattern;

/**
 * @author Joe Hendrix
 */
public class TopMenu extends MenuBar {

    private static final Logger LOG = Logger.getLogger(TopMenu.class.getName());
    private static final ExtensionFilter cssExtensionFilter = new ExtensionFilter("Cascading Style Sheet", "*.css");
    private static final String rules = "1.\tAny live cell with fewer than two live neighbours dies, as if caused by " +
            "under-population.\n" +
            "2.\tAny live cell with two or three live neighbours lives on to the next generation.\n" +
            "3.\tAny live cell with more than three live neighbours dies, as if by over-population.\n" +
            "4.\tAny dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.";
    private static final ExtensionFilter textExtensionFilter = new ExtensionFilter("Text file", "*.txt");
    private Consumer<Pattern> patternConsumer;

    public TopMenu(Consumer<Pattern> patternConsumer) {
        this.patternConsumer = patternConsumer;
        getMenus().addAll(generateFileMenu(), generateHelpMenu());
    }

    private void addPattern() {
        try {
            FileChooser patternChooser = new FileChooser();
            patternChooser.setTitle("Choose pattern...");
            patternChooser.getExtensionFilters().add(textExtensionFilter);
            File file = patternChooser.showOpenDialog(null);

            if (file != null) {
                List<String> pattern = Files.readAllLines(Paths.get(file.toURI()));
                String name = file.getName().replace(".txt", "");
                patternConsumer.accept(new Pattern(name, pattern));
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getClass().getName(), e);
        }
    }

    private Alert generateAboutDialog() {
        Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
        aboutDialog.setTitle("About");
        aboutDialog.setHeaderText("Conway's Game of Life");
        aboutDialog.setContentText(rules);
        return aboutDialog;
    }

    private Menu generateFileMenu() {
        Menu fileMenu = new Menu("File");

        MenuItem patternItem = new MenuItem("Load pattern");
        patternItem.setOnAction(action -> addPattern());

        MenuItem savePatternItem = new MenuItem("Save pattern");
        savePatternItem.setOnAction(action -> savePattern());

        MenuItem cssItem = new MenuItem("Load CSS");
        cssItem.setOnAction(action -> loadCss());

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(action -> System.exit(0));

        fileMenu.getItems().addAll(patternItem, savePatternItem, cssItem, new SeparatorMenuItem(), exitItem);

        return fileMenu;
    }

    private Menu generateHelpMenu() {
        Menu helpMenu = new Menu("Help");

        MenuItem aboutItem = new MenuItem("About");
        helpMenu.getItems().add(aboutItem);
        aboutItem.setOnAction(action -> generateAboutDialog().show());

        return helpMenu;
    }

    private void loadCss() {
        FileChooser cssChooser = new FileChooser();
        cssChooser.setTitle("Choose CSS...");
        cssChooser.getExtensionFilters().add(cssExtensionFilter);
        File file = cssChooser.showOpenDialog(null);

        if (file != null) {

        }
    }

    private void savePattern() {
        FileChooser patternSaver = new FileChooser();
        patternSaver.setTitle("Save pattern...");
        patternSaver.getExtensionFilters().add(textExtensionFilter);
        File file = patternSaver.showSaveDialog(null);

        if (file != null) {

        }
    }
}
