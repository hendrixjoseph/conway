package edu.wright.hendrix11.conway.logic;

/**
 * @author Joe Hendrix
 */
public class Cell {
    private boolean alive = false;
    private Cell northernCell;
    private Cell southernCell;
    private Cell easternCell;
    private Cell westernCell;

    public Cell() {
    }

    public Cell(boolean alive) {
        this.alive = alive;
    }

    public void setEasternCell(Cell easternCell) {
        easternCell.westernCell = this;
        this.easternCell = easternCell;
    }

    public void setNorthernCell(Cell northernCell) {
        northernCell.southernCell = this;
        this.northernCell = northernCell;
    }

    public void setSouthernCell(Cell southernCell) {
        southernCell.northernCell = this;
        this.southernCell = southernCell;
    }

    public void setWesternCell(Cell westernCell) {
        westernCell.easternCell = this;
        this.westernCell = westernCell;
    }

    public Cell getEasternCell() {
        return easternCell;
    }

    public Cell getNorthernCell() {
        return northernCell;
    }

    public Cell getSouthernCell() {
        return southernCell;
    }

    public Cell getWesternCell() {
        return westernCell;
    }

    public void toggle() {
        alive = !alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getNumberLivingNeighbors() {
        int count = 0;

        if(northernCell != null && northernCell.isAlive()) {
            count++;
        }

        if(southernCell != null && southernCell.isAlive()) {
            count++;
        }

        if(easternCell != null && easternCell.isAlive()) {
            count++;
        }

        if(westernCell != null && westernCell.isAlive()) {
            count++;
        }

        return count;
    }

    @Override
    public int hashCode() {
        return alive ? 1 : 0;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }
}
