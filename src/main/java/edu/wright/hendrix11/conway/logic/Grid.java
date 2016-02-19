package edu.wright.hendrix11.conway.logic;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Joe Hendrix
 */
public class Grid extends Thread {
    private static final Logger LOG = Logger.getLogger(Grid.class.getName());
    private Set<Cell> aliveCells = new HashSet<>();
    private long waitValue = 500;
    private volatile boolean running = false;

    private boolean classInv() {
        boolean invariant = true;

        for (Cell cell : aliveCells) {
            if (!cell.isAlive()) {
                invariant = false;
            }
        }

        return invariant;
    }

    public void setWaitValue(long waitValue) {
        this.waitValue = waitValue;
    }

    public void clear() {
        // The copy prevents a java.util.ConcurrentModificationException
        Set<Cell> copy = new HashSet<>(aliveCells);
        copy.forEach(cell -> cell.toggle());
    }

    public void addLivingCell(Cell cell) {
        assert cell.isAlive();
        aliveCells.add(cell);
        assert classInv();
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
        assert classInv();
    }

    private void toggleCells(Set<Cell> toggleTheseCells) {
        toggleTheseCells.forEach(cell -> cell.toggle());
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

    public void kill() {
        running = false;
    }

    public void restart() {
        running = true;
        System.err.println("restarting");
    }

    @Override
    public void run() {
        running = true;

        while(true) {
            while (running) {
                try {
                    tick();
                    Thread.sleep(waitValue);
                } catch (InterruptedException e) {
                    LOG.log(Level.SEVERE, e.getClass().getName(), e);
                }
            }
        }
    }
}
