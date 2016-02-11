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
            if(cell.getNorthernCell() != null && !cell.getNorthernCell().isAlive()) {
                if(checkDeadCell(cell.getNorthernCell())) {
                    newbornCells.add(cell.getNorthernCell());
                }
            }
            
            if(cell.getSouthernCell() != null && !cell.getSouthernCell().isAlive()) {
                if(checkDeadCell(cell.getSouthernCell())) {
                    newbornCells.add(cell.getSouthernCell());
                }
            }
            
            if(cell.getEasternCell() != null && !cell.getEasternCell().isAlive()) {
                if(checkDeadCell(cell.getEasternCell())) {
                    newbornCells.add(cell.getEasternCell());
                }
            }
            
            if(cell.getWesternCell() != null && !cell.getWesternCell().isAlive()) {
                if(checkDeadCell(cell.getWesternCell())) {
                    newbornCells.add(cell.getWesternCell());
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
    
    private void checkLivingCells(List<Cell> toggleTheseCells) {
        for(Cell cell : aliveCells) {
            if(checkLivingCell(cell)) {
                toggleTheseCells.add(cell);
            }
        }
    }

}
