package edu.wright.hendrix11.conway.gui;

import javafx.scene.layout.GridPane;

import edu.wright.hendrix11.conway.logic.Cell;
import edu.wright.hendrix11.conway.logic.Grid;

/**
 * @author Joe Hendrix
 */
public class VisibleGrid extends GridPane {

    private CellPane[][] cells;
    private Grid gameGrid;

    public VisibleGrid(Grid gameGrid, int rows, int columns) {
        this.gameGrid = gameGrid;

        createCells(rows, columns, gameGrid);
    }

    public VisibleGrid(Grid gameGrid, int size) {
        this(gameGrid, size, size);
    }

    private void createCells(int rows, int columns, Grid gameGrid) {
        cells = new CellPane[rows][columns];

        Cell outerloopCell = new Cell(gameGrid);

        for (int i = 0; i < rows; i++) {

            Cell innerloopCell = outerloopCell;

            for (int j = 0; j < columns; j++) {

                CellPane newCellPane = new CellPane(innerloopCell);
                newCellPane.setOnMouseClicked(e -> clickCell(newCellPane));

                this.add(cells[i][j] = newCellPane, i, j);

                innerloopCell = innerloopCell.getSouthernCell();
            }

            outerloopCell = outerloopCell.getEasternCell();
        }
    }

    private void clickCell(CellPane cellPane) {
        cellPane.toggle();
    }

    public void tick() {
        gameGrid.tick();

        for (CellPane[] c : cells) {
            for (CellPane cell : c) {
                cell.setStyle();
            }
        }
    }
}
