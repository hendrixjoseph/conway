package edu.wright.hendrix11.conway.gui;

import edu.wright.hendrix11.conway.logic.Cell;
import edu.wright.hendrix11.conway.logic.Grid;
import javafx.scene.layout.Pane;

/**
 * @author Joe Hendrix
 */
public class CellPane extends Pane {

    private Cell cell;

    public CellPane(Grid grid) {
        cell = new Cell(grid);
        setPrefSize(100.0f, 100.0f);
        setStyle();
    }

    public void toggle() {
        cell.toggle();
        setStyle();
    }

    public void setStyle() {
        if (cell.isAlive()) {
            setStyle("-fx-background-color: black; -fx-border-color: black;");
        } else {
            assert !cell.isAlive();
            setStyle("-fx-background-color: transparent; -fx-border: none;");
        }
    }

    public Cell getCell() {
        return cell;
    }
}
