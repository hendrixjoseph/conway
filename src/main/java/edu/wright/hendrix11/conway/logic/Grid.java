package edu.wright.hendrix11.conway.logic;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Joe Hendrix
 */
public class Grid {

    private Set<Cell> aliveCells = new HashSet<>();
    private int generation = 0;

    /**
     * All cells in aliveCells must be alive.
     * The current generation can never be negative.
     */ 
    private boolean classInv() {
        boolean invariant = true;

        for (Cell cell : aliveCells) {
            if (!cell.isAlive()) {
                invariant = false;
            }
        }
        
        if(generation < 0) {
            invariant = false;
        }

        return invariant;
    }

    /**
     * Adds a living cell to the aliveCells set. This method is package-private and is called explicity by
     * the {@link Cell} class.
     * 
     * precondition:    cell is alive
     * postcondition:   cell is in aliveCells
     * 
     */
    void addLivingCell(Cell cell) {
        assert cell.isAlive();
        
        aliveCells.add(cell);
        
        assert aliveCells.contains(cell);
        assert classInv();
    }

    /**
     * Empties the aliveCells set by toggling each living cell. The now-dead cell will remove itself
     * from the aliveCell set.
     * 
     * precondition:    aliveCells is not null
     * postcondition:   aliveCells is empty and all cells that were in aliveCells are not alive
     *
     */
    public void clear() {
        // The copy prevents a java.util.ConcurrentModificationException
        Set<Cell> copy = new HashSet<>(aliveCells);
        copy.forEach(Cell::toggle);
        assert aliveCells.isEmpty();
        assert classInv();
    }

    /**
     * Returns the current generation.
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Removes a dead cell from the aliveCells set. This method is package-private and is called explicity by
     * the {@link Cell} class.
     * 
     * precondition:    cell is not alive
     * postcondition:   cell is not in aliveCells
     * 
     */
    void removeDeadCell(Cell cell) {
        assert !cell.isAlive();
        
        aliveCells.remove(cell);
        
        assert !aliveCells.contains(cell);
        assert classInv();
    }

    public void tick() {
        assert classInv();
        
        Set<Cell> toggleTheseCells = new HashSet<>();

        checkLivingCells(toggleTheseCells);
        checkAdjacentDeadCells(toggleTheseCells);

        toggleCells(toggleTheseCells);

        generation++;

        assert classInv();
    }

    private void checkAdjacentDeadCells(Set<Cell> toggleTheseCells) {
        aliveCells.forEach(cell -> cell.getNeighbors().forEach(neighbor -> {
            if (!neighbor.isAlive() && checkDeadCell(neighbor)) {
                    toggleTheseCells.add(neighbor);
            }
        }));
    }

    /**
     * 
     * precondition:    
     * postcondition:   
     * 
     */
    private boolean checkDeadCell(Cell deadCell) {
        assert !deadCell.isAlive();

        int numberOfLivingNeighbors = deadCell.getNumberLivingNeighbors();

        assert numberOfLivingNeighbors >= 0 && numberOfLivingNeighbors <= 8;

        return numberOfLivingNeighbors == 3;
    }

    /**
     * 
     * precondition:    
     * postcondition:   
     * 
     */
    private boolean checkLivingCell(Cell livingCell) {
        assert livingCell.isAlive();

        int numberOfLivingNeighbors = livingCell.getNumberLivingNeighbors();

        assert numberOfLivingNeighbors >= 0 && numberOfLivingNeighbors <= 8;

        return numberOfLivingNeighbors < 2 || numberOfLivingNeighbors > 3;
    }

    /**
     * 
     * precondition:    
     * postcondition:   
     * 
     */
    private void checkLivingCells(Set<Cell> toggleTheseCells) {
        aliveCells.forEach(cell ->  {
            if (checkLivingCell(cell)) {
                toggleTheseCells.add(cell);
            }
        });
    }

    private void toggleCells(Set<Cell> toggleTheseCells) {
        toggleTheseCells.forEach(Cell::toggle);
    }
}
