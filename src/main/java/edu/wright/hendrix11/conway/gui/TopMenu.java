package edu.wright.hendrix11.conway.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;

/**
 * @author Joe Hendrix
 */
public class TopMenu extends MenuBar {
    public TopMenu() {
        getMenus().addAll(generateFileMenu(), generateHelpMenu());
    }

    private Menu generateHelpMenu() {
        Menu helpMenu = new Menu("Help");

        MenuItem aboutItem = new MenuItem("About");
        helpMenu.getItems().add(aboutItem);
        aboutItem.setOnAction(action -> generateAboutDialog().show());

        return helpMenu;
    }

    private Alert generateAboutDialog() {
        Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
        aboutDialog.setTitle("About");
        aboutDialog.setHeaderText("Conway's Game of Life");
        aboutDialog.setContentText(rules());
        return aboutDialog;
    }

    private String rules() {
        StringBuilder rules = new StringBuilder("1.\tAny live cell with fewer than two live neighbours dies, as if caused by under-population.\n");
        rules.append("2.\tAny live cell with two or three live neighbours lives on to the next generation.\n");
        rules.append("3.\tAny live cell with more than three live neighbours dies, as if by over-population.\n");
        rules.append("4.\tAny dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.");
        return rules.toString();
    }

    private Menu generateFileMenu() {
        Menu fileMenu = new Menu("File");

        FileChooser patternChooser = new FileChooser();
        patternChooser.setTitle("Choose pattern...");

        MenuItem patternItem = new MenuItem("Load pattern");
        patternItem.setOnAction(action -> patternChooser.showOpenDialog(null));

        fileMenu.getItems().addAll(patternItem,new MenuItem("Save pattern"),new MenuItem("Load CSS"));

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(action -> System.exit(0));
        fileMenu.getItems().add(exitItem);

        return fileMenu;
    }
}
