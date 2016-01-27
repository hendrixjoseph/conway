package edu.wright.hendrix11.conway.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joe Hendrix
 */
public class Grid {
    private Map<Coordinate, Cell> aliveCells = new HashMap<>();
    private Map<Coordinate, Cell> deadAdjacentCells;
    private Map<Coordinate, Cell> changedCells;

    public Grid() {
    }

    public Grid(Map<Coordinate, Cell> aliveCells) {
        this.aliveCells = aliveCells;
    }

    public Grid(List<Coordinate> liveCellCoordinates) {
        for(Coordinate coordinate : liveCellCoordinates) {
            aliveCells.put(coordinate, new Cell());
        }
    }

    public Map<Coordinate, Cell> tick() {
        changedCells = new HashMap<>();

        countLivingNeighbors();
        tickCells(aliveCells);
        tickCells(deadAdjacentCells);

        return changedCells;
    }

    private void tickCells(Map<Coordinate, Cell> cells) {
        for(Map.Entry<Coordinate, Cell> entry : cells.entrySet()) {
            Cell cell = entry.getValue();
            Coordinate coord = entry.getKey();

            boolean aliveBefore = cell.isAlive();

            cell.tick();

            if(cell.isAlive() != aliveBefore) {
                changedCells.put(coord,cell);
            }
        }
    }

    private void countLivingNeighbors() {
        deadAdjacentCells = new HashMap<>();

        for(Map.Entry<Coordinate, Cell> entry : aliveCells.entrySet()) {
            for(Coordinate neighbor : entry.getKey().getNeighbors()) {
                if(aliveCells.get(neighbor) != null) {
                    entry.getValue().incrementAliveNeighbors();
                } else {
                    if(deadAdjacentCells.get(neighbor) != null) {
                        deadAdjacentCells.get(neighbor).incrementAliveNeighbors();
                    } else {
                        Cell deadCell = new Cell(false);
                        deadCell.incrementAliveNeighbors();
                        deadAdjacentCells.put(neighbor, deadCell);
                    }
                }
            }
        }
    }
}
