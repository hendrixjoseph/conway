package edu.wright.hendrix11.conway.gui;

import edu.wright.hendrix11.conway.logic.Grid;
import javafx.scene.control.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * @author Joe Hendrix
 */
public class VisibleGrid extends GridPane {

    private CellPane[] cells;
    private Grid gameGrid;

    public VisibleGrid(Grid gameGrid, int rows, int columns) {
        this.gameGrid = gameGrid;

        cells = new CellPane[rows * columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int pos = j + i * columns;

                assert pos < cells.length;
                assert pos % columns == j;

                CellPane newCell = new CellPane();
                newCell.setOnMouseClicked(e -> clickCell(newCell));

                if(i > 0) {
                    int west = j + (i - 1) * columns;
                    assert west < pos;
                    //assert pos % rows == i;
                    assert west % columns == j;

                    newCell.getCell().setWesternCell(cells[west].getCell());
                }

                if(j > 0) {
                    int north = (j - 1) + i * columns;
                    assert north < pos;
                    //assert pos % rows == i;
                    assert north % columns == j - 1;

                    newCell.getCell().setNorthernCell(cells[north].getCell());
                }

                this.add(cells[pos] = newCell, j, i);
            }
        }
    }

    public VisibleGrid(Grid gameGrid, int size) {
        this(gameGrid, size, size);
    }

    private void clickCell(CellPane cellPane) {
        cellPane.toggle();

        if(cellPane.getCell().isAlive()) {
            gameGrid.addLivingCell(cellPane.getCell());
        } else {
            assert !cellPane.getCell().isAlive();
            gameGrid.removeDeadCell(cellPane.getCell());
        }
    }

    public void tick() {
        gameGrid.tick();

        for(CellPane cellPane : cells) {
            cellPane.setStyle();
        }
    }
}
