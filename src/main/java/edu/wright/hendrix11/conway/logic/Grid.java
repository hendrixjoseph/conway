package edu.wright.hendrix11.conway.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Joe Hendrix
 */
public class Grid {
    private Set<Cell> aliveCells = new HashSet<>();

    public void addLivingCell(Cell cell) {
        assert cell.isAlive();
        aliveCells.add(cell);
    }

    public void removeDeadCell(Cell cell) {
        assert !cell.isAlive();
        aliveCells.remove(cell);
    }

    public void tick() {
        Set<Cell> toggleTheseCells = new HashSet<>();

        checkLivingCells(toggleTheseCells);
        checkAdjacentDeadCells(toggleTheseCells);

        toggleCells(toggleTheseCells);
    }
    
    private void toggleCells(Set<Cell> toggleTheseCells) {
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
    
    private void checkAdjacentDeadCells(Set<Cell> toggleTheseCells) {
        for(Cell cell : aliveCells) {
            for(Cell neighbor : cell.getNeighbors()) {
                if(neighbor != null && !neighbor.isAlive()) {
                    if(checkDeadCell(neighbor)) {
                        toggleTheseCells.add(neighbor);
                    }
                } else if(neighbor == null) {
                    // Todo: this case
                }
            }
        }
    }
    
    private boolean checkDeadCell(Cell deadCell) {
        assert !deadCell.isAlive();
        
        int numberOfLivingNeighbors = deadCell.getNumberLivingNeighbors();
        
        assert numberOfLivingNeighbors >= 0 && numberOfLivingNeighbors <= 8;
        
        return numberOfLivingNeighbors == 3;
    }
    
    private boolean checkLivingCell(Cell livingCell) {
        assert livingCell.isAlive();
        
        int numberOfLivingNeighbors = livingCell.getNumberLivingNeighbors();
        
        assert numberOfLivingNeighbors >= 0 && numberOfLivingNeighbors <= 8;
        
        return numberOfLivingNeighbors < 2 || numberOfLivingNeighbors > 3;
    }
    
    private void checkLivingCells(Set<Cell> toggleTheseCells) {
        for(Cell cell : aliveCells) {
            if(checkLivingCell(cell)) {
                toggleTheseCells.add(cell);
            }
        }
    }
}
