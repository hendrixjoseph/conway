package edu.wright.hendrix11.conway.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Joe Hendrix
 */
public class Cell {
    private Grid grid;
    private boolean alive = false;
    private Cell northernCell;
    private Cell southernCell;
    private Cell easternCell;
    private Cell westernCell;

    public Cell(Grid grid) {
        this.grid = grid;
    }

    private boolean classInv() {
        boolean invariant = true;

        if (northernCell != null && !Objects.equals(northernCell.southernCell, this)) {
            invariant = false;
        }

        if (southernCell != null && !Objects.equals(southernCell.northernCell, this)) {
            invariant = false;
        }

        if (easternCell != null && !Objects.equals(easternCell.westernCell, this)) {
            invariant = false;
        }

        if (westernCell != null && !Objects.equals(westernCell.easternCell, this)) {
            invariant = false;
        }

        if (northernCell != null && easternCell != null) {
            if (!Objects.equals(northernCell.easternCell, easternCell.northernCell)) {
                invariant = false;
            }
        }

        if (northernCell != null && westernCell != null) {
            if (!Objects.equals(northernCell.westernCell, westernCell.northernCell)) {
                invariant = false;
            }
        }

        if (southernCell != null && easternCell != null) {
            if (!Objects.equals(southernCell.easternCell, easternCell.southernCell)) {
                invariant = false;
            }
        }

        if (southernCell != null && westernCell != null) {
            if (!Objects.equals(southernCell.westernCell, southernCell.westernCell)) {
                invariant = false;
            }
        }

        return invariant;
    }

    public Set<Cell> getNeighbors() {
        grow();

        Set<Cell> neighbors = new HashSet<>();

        neighbors.add(northernCell);
        neighbors.add(southernCell);
        neighbors.add(easternCell);
        neighbors.add(westernCell);

        neighbors.add(northernCell.easternCell);
        neighbors.add(northernCell.westernCell);
        neighbors.add(southernCell.easternCell);
        neighbors.add(southernCell.westernCell);

        assert neighbors.size() == 8;
        assert classInv();

        return neighbors;
    }

    private void grow() {
        growNorth();
        growSouth();
        growEast();
        growWest();
    }

    private void setNorthernCell(Cell northernCell) {
        northernCell.southernCell = this;
        this.northernCell = northernCell;
    }

    private void setWesternCell(Cell westernCell) {
        westernCell.easternCell = this;
        this.westernCell = westernCell;
    }

    private void setEasternCell(Cell easternCell) {
        easternCell.westernCell = this;
        this.easternCell = easternCell;
    }

    private void setSouthernCell(Cell southernCell) {
        southernCell.northernCell = this;
        this.southernCell = southernCell;
    }

    public Cell getNorthernCell() {
        return northernCell;
    }

    public Cell getEasternCell() {
        return easternCell;
    }

    public Cell getSouthernCell() {
        return southernCell;
    }

    public Cell getWesternCell() {
        return westernCell;
    }

    public void growNorth() {
        if(northernCell == null) {
            setNorthernCell(new Cell(grid));

            if(easternCell != null) {
                northernCell.setEasternCell(new Cell(grid));
                easternCell.setNorthernCell(northernCell.easternCell);
            }

            if(westernCell != null) {
                northernCell.setWesternCell(new Cell(grid));
                westernCell.setNorthernCell(northernCell.westernCell);
            }
        }

        assert classInv();
    }

    public void growSouth() {
        if(southernCell == null) {
            setSouthernCell(new Cell(grid));

            if(easternCell != null) {
                southernCell.setEasternCell(new Cell(grid));
                easternCell.setSouthernCell(southernCell.easternCell);
            }

            if(westernCell != null) {
                southernCell.setWesternCell(new Cell(grid));
                westernCell.setSouthernCell(southernCell.westernCell);
            }
        }

        assert classInv();
    }

    public void growEast() {
        if(easternCell == null) {
            setEasternCell(new Cell(grid));

            if(northernCell != null) {
                easternCell.setNorthernCell(new Cell(grid));
                northernCell.setEasternCell(easternCell.northernCell);
            }

            if(southernCell != null) {
                easternCell.setSouthernCell(new Cell(grid));
                southernCell.setEasternCell(easternCell.southernCell);
            }
        }

        assert classInv();
    }

    public void growWest() {
        if(westernCell == null) {
            setWesternCell(new Cell(grid));

            if(northernCell != null) {
                westernCell.setNorthernCell(new Cell(grid));
                northernCell.setWesternCell(westernCell.northernCell);
            }

            if(southernCell != null) {
                westernCell.setSouthernCell(new Cell(grid));
                southernCell.setWesternCell(westernCell.southernCell);
            }
        }

        assert classInv();
    }

    public void toggle() {
        alive = !alive;

        if (alive) {
            grid.addLivingCell(this);
        } else {
            grid.removeDeadCell(this);
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public int getNumberLivingNeighbors() {
        int count = 0;

        for (Cell neighbor : getNeighbors()) {
            if (neighbor != null && neighbor.isAlive()) {
                count++;
            }
        }

        assert count >= 0 && count <= 8;

        return count;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }
}
