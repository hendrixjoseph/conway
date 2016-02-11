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
    
    private void checkAdjacentDeadCells(List<Cell> toggleTheseCells) {
        Set<Cell> newbornCells = new HashSet<>();
        
        for(Cell cell : aliveCells) {
            checkDeadCell(cell.getNorthernCell(), newbornCells);
            checkDeadCell(cell.getSouthernCell(), newbornCells);
            checkDeadCell(cell.getEasternCell(), newbornCells);
            checkDeadCell(cell.getWesthernCell(), newbornCells);
            
            checkDeadCell(cell.getNorthEasternCell(), newbornCells);
            checkDeadCell(cell.getNorthWesternCell(), newbornCells);
            checkDeadCell(cell.getSouthEasternCell(), newbornCells);
            checkDeadCell(cell.getSouthWesternCell(), newbornCells);
        }
        
        toggleTheseCells.addAll(newbornCells);
    }
    
    private void checkDeadCell(Cell deadCell, Set<Cell> newbornCells) {
        if(deadCell != null && deadCell.isAlive()) {
            return;   
        }
        
        assert deadCell == null || !deadCell.isAlive();
        
        if(deadCell == null) {
            // Todo: handle this case
            return;
        }
        
        int numberOfLivingNeighbors = deadCell.getNumberLivingNeighbors();
        
        assert numberOfLivingNeighbors >= 0 && numberOfLivingNeighbors <= 8;
        
        if(numberOfLivingNeighbors == 3) {
            newbornCells.add(cell.getNorthernCell());
        }
    }
    
    private void checkLivingCell(Cell livingCell, List<Cell> toggleTheseCells) {
        assert livingCell.isAlive();
        
        int numberOfLivingNeighbors = livingCell.getNumberLivingNeighbors();
        
        assert numberOfLivingNeighbors >= 0 && numberOfLivingNeighbors <= 8;
        
        if(numberOfLivingNeighbors < 2 || numberOfLivingNeighbors > 3) {
            toggleTheseCells.add(livingCell);
        }
    }
    
    private void checkLivingCells(List<Cell> toggleTheseCells) {
        for(Cell cell : aliveCells) {
            checkLivingCell(cell, toggleTheseCells);
        }
    }

}
