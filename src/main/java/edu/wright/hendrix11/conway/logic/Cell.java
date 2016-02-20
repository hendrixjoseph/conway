package edu.wright.hendrix11.conway.logic;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Joe Hendrix
 */
public class Cell {
    private static final Logger LOG = Logger.getLogger(Cell.class.getName());

    private final Grid grid;
    private boolean alive = false;
    private Cell easternCell;
    private Cell northernCell;
    private Cell southernCell;
    private Consumer<Void> toggleHandler;
    private Cell westernCell;

    public Cell(Grid grid) {
        this.grid = grid;
    }

    private boolean classInv() {
        boolean invariant = true;

        if (northernCell != null && !Objects.equals(northernCell.southernCell, this)) {
            LOG.log(Level.SEVERE, "northernCell.southernCell != this");
            invariant = false;
        }

        if (southernCell != null && !Objects.equals(southernCell.northernCell, this)) {
            LOG.log(Level.SEVERE, "southernCell.northernCell != this");
            invariant = false;
        }

        if (easternCell != null && !Objects.equals(easternCell.westernCell, this)) {
            LOG.log(Level.SEVERE, "easternCell.westernCell != this");
            invariant = false;
        }

        if (westernCell != null && !Objects.equals(westernCell.easternCell, this)) {
            LOG.log(Level.SEVERE, "westernCell.easternCell != this");
            invariant = false;
        }

        if (northernCell != null && easternCell != null && !Objects.equals(northernCell.easternCell, easternCell
                .northernCell)) {
            LOG.log(Level.SEVERE, "northernCell.easternCell != easternCell.northernCell");
            invariant = false;
        }

        if (northernCell != null && westernCell != null && !Objects.equals(northernCell.westernCell, westernCell
                .northernCell)) {
            LOG.log(Level.SEVERE, "northernCell.westernCell != westernCell.northernCell");
            invariant = false;
        }

        if (southernCell != null && easternCell != null && !Objects.equals(southernCell.easternCell, easternCell
                .southernCell)) {
            LOG.log(Level.SEVERE, "southernCell.easternCell != easternCell.southernCell");
            invariant = false;
        }

        if (southernCell != null && westernCell != null && !Objects.equals(southernCell.westernCell, westernCell
                .southernCell)) {
            LOG.log(Level.SEVERE, "southernCell.westernCell != westernCell.southernCell");
            invariant = false;
        }

        return invariant;
    }

    public Cell getEasternCell() {
        if (easternCell == null) {
            setEasternCell(new Cell(grid));

            if (northernCell != null) {
                if (northernCell.easternCell != null) {
                    easternCell.setNorthernCell(northernCell.easternCell);
                } else {
                    easternCell.setNorthernCell(new Cell(grid));
                    northernCell.setEasternCell(easternCell.northernCell);
                }
            }

            if (southernCell != null) {
                if (southernCell.easternCell != null) {
                    easternCell.setSouthernCell(southernCell.easternCell);
                } else {
                    easternCell.setSouthernCell(new Cell(grid));
                    southernCell.setEasternCell(easternCell.southernCell);
                }
            }
        }

        assert classInv();

        return easternCell;
    }

    public Cell getNorthernCell() {
        if (northernCell == null) {
            setNorthernCell(new Cell(grid));

            if (easternCell != null) {
                if (easternCell.northernCell != null) {
                    northernCell.setEasternCell(easternCell.northernCell);
                } else {
                    northernCell.setEasternCell(new Cell(grid));
                    easternCell.setNorthernCell(northernCell.easternCell);
                }
            }

            if (westernCell != null) {
                if (westernCell.northernCell != null) {
                    northernCell.setWesternCell(westernCell.northernCell);
                } else {
                    northernCell.setWesternCell(new Cell(grid));
                    westernCell.setNorthernCell(northernCell.westernCell);
                }
            }
        }

        assert classInv();

        return northernCell;
    }

    public Cell getSouthernCell() {
        if (southernCell == null) {
            setSouthernCell(new Cell(grid));

            if (easternCell != null) {
                if (easternCell.southernCell != null) {
                    southernCell.setEasternCell(easternCell.southernCell);
                } else {
                    southernCell.setEasternCell(new Cell(grid));
                    easternCell.setSouthernCell(southernCell.easternCell);
                }
            }

            if (westernCell != null) {
                if (westernCell.southernCell != null) {
                    southernCell.setWesternCell(westernCell.southernCell);
                } else {
                    southernCell.setWesternCell(new Cell(grid));
                    westernCell.setSouthernCell(southernCell.westernCell);
                }
            }
        }

        assert classInv();

        return southernCell;
    }

    public Cell getWesternCell() {
        if (westernCell == null) {
            setWesternCell(new Cell(grid));

            if (northernCell != null) {
                if (northernCell.westernCell != null) {
                    westernCell.setNorthernCell(northernCell.westernCell);
                } else {
                    westernCell.setNorthernCell(new Cell(grid));
                    northernCell.setWesternCell(westernCell.northernCell);
                }
            }

            if (southernCell != null) {
                if (southernCell.westernCell != null) {
                    westernCell.setSouthernCell(southernCell.westernCell);
                } else {
                    westernCell.setSouthernCell(new Cell(grid));
                    southernCell.setWesternCell(westernCell.southernCell);
                }
            }
        }

        assert classInv();

        return westernCell;
    }

    public Set<Cell> getNeighbors() {
        Set<Cell> neighbors = new HashSet<>();

        neighbors.add(getNorthernCell());
        neighbors.add(getSouthernCell());
        neighbors.add(getEasternCell());
        neighbors.add(getWesternCell());

        neighbors.add(northernCell.getEasternCell());
        neighbors.add(northernCell.getWesternCell());
        neighbors.add(southernCell.getEasternCell());
        neighbors.add(southernCell.getWesternCell());

        assert neighbors.size() == 8 && northernCell != null && westernCell != null && easternCell != null &&
                southernCell != null && northernCell.easternCell != null && northernCell.westernCell != null &&
                southernCell.easternCell != null && southernCell.westernCell != null : "\nneighbors.size(): " +
                neighbors.size() + "\nnorthernCell: " + northernCell + "\nwesternCell: " + westernCell +
                "\neasternCell: " + easternCell + "\nsouthernCell: " + southernCell + "\nnorthernCell.easternCell: "
                + northernCell.easternCell + "\nnorthernCell.westernCell: " + northernCell.westernCell +
                "\nsouthernCell.easternCell: " + southernCell.easternCell + "\nsouthernCell.westernCell: " +
                southernCell.westernCell;
        assert classInv();

        return neighbors;
    }

    public int getNumberLivingNeighbors() {
        int count = 0;

        for (Cell neighbor : getNeighbors()) {
            if (neighbor.isAlive()) {
                count++;
            }
        }

        assert count >= 0 && count <= 8;

        return count;
    }

    public boolean isAlive() {
        return alive;
    }

    public void onToggle(Consumer<Void> toggleHandler) {
        this.toggleHandler = toggleHandler;
    }

    public void toggle() {
        alive = !alive;

        if (alive) {
            grid.addLivingCell(this);
        } else {
            grid.removeDeadCell(this);
        }

        if (toggleHandler != null) {
            toggleHandler.accept(null);
        }
    }

    private void setEasternCell(Cell easternCell) {
        assert this.easternCell == null : "Eastern cell already set!";

        easternCell.westernCell = this;
        this.easternCell = easternCell;

        assert Objects.equals(this.easternCell.westernCell, this);
    }

    private void setNorthernCell(Cell northernCell) {
        assert this.northernCell == null : "Northern cell already set!";

        northernCell.southernCell = this;
        this.northernCell = northernCell;

        assert Objects.equals(this.northernCell.southernCell, this);
    }

    private void setSouthernCell(Cell southernCell) {
        assert this.southernCell == null : "Southern cell already set!";

        southernCell.northernCell = this;
        this.southernCell = southernCell;

        assert Objects.equals(this.southernCell.northernCell, this);
    }

    private void setWesternCell(Cell westernCell) {
        assert this.westernCell == null : "Western cell already set!";

        westernCell.easternCell = this;
        this.westernCell = westernCell;

        assert Objects.equals(this.westernCell.easternCell, this);
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
