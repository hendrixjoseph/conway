package edu.wright.hendrix11.conway.gui;

import javafx.scene.layout.GridPane;

import edu.wright.hendrix11.conway.logic.Cell;
import edu.wright.hendrix11.conway.logic.Grid;

/**
 * @author Joe Hendrix
 */
public class VisibleGrid extends GridPane {

    private Grid gameGrid = new Grid();

    public VisibleGrid(int rows, int columns) {
        createCells(rows, columns, gameGrid);
    }

    public VisibleGrid(int size) {
        this(size, size);
    }

    private void createCells(int rows, int columns, Grid gameGrid) {

        Cell outerloopCell = new Cell(gameGrid);

        for (int i = 0; i < rows; i++) {

            Cell innerloopCell = outerloopCell;

            for (int j = 0; j < columns; j++) {

                this.add(new CellPane(innerloopCell), i, j);

                innerloopCell = innerloopCell.getSouthernCell();
            }

            outerloopCell = outerloopCell.getEasternCell();
        }
    }

    public Grid getGameGrid() {
        return gameGrid;
    }
}
