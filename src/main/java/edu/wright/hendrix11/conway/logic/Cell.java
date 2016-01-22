package edu.wright.hendrix11.conway.logic;

/**
 * @author Joe Hendrix
 */
public class Cell {
    private Integer numAliveNeighbors;
    private boolean alive;

    public Cell() {
        alive = true;
    }

    public Cell(boolean alive) {
        this.alive = alive;
    }

    public void incrementAliveNeighbors() {
        if(numAliveNeighbors == null) {
            numAliveNeighbors = 1;
        } else {
            numAliveNeighbors++;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void tick() {
        if(numAliveNeighbors == null) {
            throw new NullPointerException("Alive neighbors not set!");
        }

        if(alive) {
            if(numAliveNeighbors < 2 || numAliveNeighbors > 3) {
                alive = false;
            }
        } else {
            if(numAliveNeighbors == 3) {
                alive = true;
            }
        }

        numAliveNeighbors = null;
    }
}
