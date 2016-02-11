package edu.wright.hendrix11.conway.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joe Hendrix
 */
public class Grid {
    private List<Cell> aliveCells = new ArrayList<>();

    public void addLivingCell(Cell cell) {
        assert cell.isAlive();
        aliveCells.add(cell);
    }

    public void removeDeadCell(Cell cell) {
        assert !cell.isAlive();
        aliveCells.remove(cell);
    }

    public void tick() {
        List<Cell> toggleTheseCells = new ArrayList<>();

        checkLivingCells(toggleTheseCells);

        toggleCells(toggleTheseCells);
    }
    
    private void toggleCells(List<Cell> toggleTheseCells) {
        for(Cell cell : toggleTheseCells) {
            cell.toggle();

            if(cell.isAlive()) {
                aliveCells.add(cell);
            } else {
                assert !cell.isAlive();
                aliveCells.remove(cell);
            }
        }
    }
    
    private void checkLivingCells(List<Cell> toggleTheseCells) {
        for(Cell cell : aliveCells) {
            int numberOfLivingNeighbors = cell.getNumberLivingNeighbors();

            if(numberOfLivingNeighbors < 2 || numberOfLivingNeighbors > 3) {
                toggleTheseCells.add(cell);
            }
        }
    }

}
