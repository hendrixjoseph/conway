package edu.wright.hendrix11.conway.gui.grid;

import javafx.scene.layout.GridPane;

import java.util.function.Supplier;

import edu.wright.hendrix11.conway.logic.Cell;
import edu.wright.hendrix11.conway.logic.Grid;
import edu.wright.hendrix11.conway.logic.Pattern;

/**
 * @author Joe Hendrix
 */
public class VisibleGrid extends GridPane {

    private final Grid gameGrid;

    public VisibleGrid(Grid gameGrid, int rows, int columns, Supplier<Pattern> patternSupplier) {
        this.gameGrid = gameGrid;
        createCells(rows, columns, gameGrid, patternSupplier);
    }

    public VisibleGrid(Grid gameGrid, int size, Supplier<Pattern> patternSupplier) {
        this(gameGrid, size, size, patternSupplier);
    }

    private void createCells(int rows, int columns, Grid gameGrid, Supplier<Pattern> patternSupplier) {

        Cell outerloopCell = new Cell(gameGrid);

        for (int i = 0; i < rows; i++) {

            Cell innerloopCell = outerloopCell;

            for (int j = 0; j < columns; j++) {

                this.add(new CellPane(innerloopCell, patternSupplier), i, j);

                innerloopCell = innerloopCell.getSouthernCell();
            }

            outerloopCell = outerloopCell.getEasternCell();
        }
    }
}
