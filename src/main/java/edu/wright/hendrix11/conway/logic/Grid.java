package edu.wright.hendrix11.conway.logic;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Joe Hendrix
 */
public class Grid {

    private Set<Cell> aliveCells = new HashSet<>();
    private int generation = 0;

    private boolean classInv() {
        boolean invariant = true;

        for (Cell cell : aliveCells) {
            if (!cell.isAlive()) {
                invariant = false;
            }
        }

        return invariant;
    }

    public void addLivingCell(Cell cell) {
        assert cell.isAlive();
        aliveCells.add(cell);
        assert classInv();
    }

    public void clear() {
        // The copy prevents a java.util.ConcurrentModificationException
        Set<Cell> copy = new HashSet<>(aliveCells);
        copy.forEach(cell -> cell.toggle());
    }

    public int getGeneration() {
        return generation;
    }

    public void removeDeadCell(Cell cell) {
        assert !cell.isAlive();
        aliveCells.remove(cell);
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
        for (Cell cell : aliveCells) {
            for (Cell neighbor : cell.getNeighbors()) {
                if (!neighbor.isAlive()) {
                    if (checkDeadCell(neighbor)) {
                        toggleTheseCells.add(neighbor);
                    }
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
        for (Cell cell : aliveCells) {
            if (checkLivingCell(cell)) {
                toggleTheseCells.add(cell);
            }
        }
    }

    private void toggleCells(Set<Cell> toggleTheseCells) {
        toggleTheseCells.forEach(cell -> cell.toggle());
    }
}
