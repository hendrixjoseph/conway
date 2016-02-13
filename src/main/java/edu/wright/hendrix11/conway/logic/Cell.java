package edu.wright.hendrix11.conway.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<Cell> getNeighbors() {
        List<Cell> neighbors = new ArrayList<>();

        neighbors.add(northernCell);
        neighbors.add(southernCell);
        neighbors.add(easternCell);
        neighbors.add(westernCell);

        neighbors.add(getNorthEasternCell());
        neighbors.add(getNorthWesternCell());
        neighbors.add(getSouthEasternCell());
        neighbors.add(getSouthWesternCell());

        assert neighbors.size() == 8;

        return neighbors;
    }

    public void setNorthernCell(Cell northernCell) {
        northernCell.southernCell = this;
        this.northernCell = northernCell;
    }

    public void setWesternCell(Cell westernCell) {
        westernCell.easternCell = this;
        this.westernCell = westernCell;
    }

    public Cell getNorthEasternCell() {
        Cell northEasternCell = null;

        if (northernCell != null) {
            northEasternCell = northernCell.easternCell;
        } else if (easternCell != null) {
            northEasternCell = easternCell.northernCell;
        }

        return northEasternCell;
    }

    public Cell getNorthWesternCell() {
        Cell northWesternCell = null;

        if (northernCell != null) {
            northWesternCell = northernCell.westernCell;
        } else if (westernCell != null) {
            northWesternCell = westernCell.northernCell;
        }

        return northWesternCell;
    }

    public Cell getSouthEasternCell() {
        Cell southEasternCell = null;

        if (southernCell != null) {
            southEasternCell = southernCell.easternCell;
        } else if (easternCell != null) {
            southEasternCell = easternCell.southernCell;
        }

        return southEasternCell;
    }

    public Cell getSouthWesternCell() {
        Cell southWesternCell = null;

        if (southernCell != null) {
            southWesternCell = southernCell.westernCell;
        } else if (westernCell != null) {
            southWesternCell = westernCell.southernCell;
        }

        return southWesternCell;
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
