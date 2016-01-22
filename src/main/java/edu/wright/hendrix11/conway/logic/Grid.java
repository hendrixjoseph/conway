package edu.wright.hendrix11.conway.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joe Hendrix
 */
public class Grid {
    private Map<Coordinate, Cell> aliveCells = new HashMap<>();

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

    public void tick() {
        Map<Coordinate, Cell> deadAdjacentCells = new HashMap<>();

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

        for(Map.Entry<Coordinate, Cell> entry : aliveCells.entrySet()) {
            Cell aliveCell = entry.getValue();

            aliveCell.tick();

            if(!aliveCell.isAlive()) {
                // remove alive cell
            }
        }

        for(Map.Entry<Coordinate, Cell> entry : deadAdjacentCells.entrySet()) {
            Cell deadCell = entry.getValue();

            deadCell.tick();

            if(deadCell.isAlive()) {
                // add newly alive cell
            }
        }
    }
}
