package edu.wright.hendrix11.conway.gui;

import javafx.scene.layout.Pane;

import edu.wright.hendrix11.conway.logic.Cell;

/**
 * @author Joe Hendrix
 */
public class CellPane extends Pane {

    private static final String DEAD_CELL_STYLE_CLASS = "deadCell";
    private static final String LIVING_CELL_STYLE_CLASS = "livingCell";
    
    private final Cell cell;

    public CellPane(Cell cell) {
        this.cell = cell;
        setPrefSize(100.0f, 100.0f);
        setStyle();
        setOnMouseClicked(event -> cell.toggle());
        cell.onToggle(event -> setStyle());
    }
    
    private void setStyleClass() {
        if(cell.isAlive()) {
            getStyleClass().remove(DEAD_CELL_STYLE_CLASS);
            getStyleClass().add(LIVING_CELL_STYLE_CLASS);
        } else {
            assert !cell.isAlive();
            getStyleClass().remove(LIVING_CELL_STYLE_CLASS);
            getStyleClass().add(DEAD_CELL_STYLE_CLASS);
        }
    }

    private void setStyle() {
        if (cell.isAlive()) {
            setStyle("-fx-background-color: black; -fx-border-color: black;");
        } else {
            assert !cell.isAlive();
            setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 1px;");
        }
    }

    public Cell getCell() {
        return cell;
    }
}
