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

    /**
     * Returns the cell to the east of this one. If this cell does not exist yet, it creates it.
     *
     * @return the eastern cell
     */
    public Cell getEasternCell() {
        if (easternCell == null) {
            createEasternCell();
        }

        assert classInv();

        return easternCell;
    }

    /**
     * Returns the cell to the north of this one. If this cell does not exist yet, it creates it.
     *
     * @return the northern cell
     */
    public Cell getNorthernCell() {
        if (northernCell == null) {
            setNorthernCell(new Cell(grid));

            if (easternCell != null) {
                createNortheasternCell();
            }

            if (westernCell != null) {
                createNorthwesternCell();
            }
        }

        assert classInv();

        return northernCell;
    }

    /**
     * Returns the cell to the south of this one. If this cell does not exist yet, it creates it.
     *
     * @return the southern cell
     */
    public Cell getSouthernCell() {
        if (southernCell == null) {
            createSouthernCell();
        }

        assert classInv();

        return southernCell;
    }

    /**
     * Returns the cell to the west of this one. If this cell does not exist yet, it creates it.
     *
     * @return the western cell
     */
    public Cell getWesternCell() {
        if (westernCell == null) {
            createWesternCell();
        }

        assert classInv();

        return westernCell;
    }

    /**
     * Returns the set of neighbors to this cell. Every cell has exactly 8 unique neighbors: four from the cardinal
     * directions and four from the diagonal directions.
     * <p>
     * <pre>
     * precondition:
     * postcondition:   the returned set is of size 8 containing each of the cardinal and diagonal cells;
     *                  none of these cells are null
     * </pre>
     *
     * @return the set of neighbors
     */
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

        assert neighbors.size() == 8 && !neighbors.contains(null) && neighbors.contains(northernCell) && neighbors
                .contains(southernCell) && neighbors.contains(easternCell) && neighbors.contains(westernCell) &&
                neighbors.contains(northernCell.easternCell) && neighbors.contains(northernCell.westernCell) &&
                neighbors.contains(southernCell.easternCell) && neighbors.contains(southernCell.westernCell);
        assert classInv();

        return neighbors;
    }

    /**
     * Counts and returns the number of living neighbors. Since every cell has exactly eight neighbors, it can have at
     * most 8 living neighbors. No neighbor is required to be alive, however, so it can also have as few as 0 living
     * neighbors.
     * <p>
     * <pre>
     * precondition:
     * postcondition:   the count ranges from 0 to 8
     * </pre>
     *
     * @return the number of living neighbors
     */
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

    /**
     * Returns true is this cell is alive, false otherwise.
     *
     * @return whether this cell is alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * @param toggleHandler
     */
    public void onToggle(Consumer<Void> toggleHandler) {
        this.toggleHandler = toggleHandler;
    }

    /**
     * Toggles the living state of this cell. If the cell was alive, after this method it is now dead. Conversely, if
     * the cell was dead, it is now alive.
     * <p>
     * This method will also add or remove the cell from the living cell list in the {@link Grid} it is associated with,
     * depending if it is now alive or dead.
     * <p>
     * <pre>
     * precondition:
     * postcondition:   before alive == after !alive
     * </pre>
     */
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

    /**
     * Creates the eastern cell.
     * <p>
     * <pre>
     * precondition:    easternCell is null
     * postcondition:   easternCell.westernCell == this cell
     * </pre>
     */
    private void createEasternCell() {
        assert easternCell == null;

        setEasternCell(new Cell(grid));

        if (northernCell != null) {
            createNortheasternCell();
        }

        if (southernCell != null) {
            createSoutheasternCell();
        }

        assert Objects.equals(easternCell.westernCell, this);
    }

    /**
     * Creates and / or sets the northeastern cell.
     * <p>
     * <pre>
     * precondition:    northernCell and easternCell is not null
     *                  northernCell.easternCell and easternCell.northernCell is null
     * postcondition:   northernCell.easternCell == easternCell.northernCell
     * </pre>
     */
    private void createNortheasternCell() {
        assert northernCell != null && easternCell != null && (northernCell.easternCell == null || easternCell
                .northernCell == null);

        if (northernCell != null && easternCell != null) {
            if (northernCell.easternCell != null) {
                easternCell.setNorthernCell(northernCell.easternCell);
            } else if (easternCell.northernCell != null) {
                northernCell.setEasternCell(easternCell.northernCell);
            } else {
                easternCell.setNorthernCell(new Cell(grid));
                northernCell.setEasternCell(easternCell.northernCell);
            }
        }

        assert Objects.equals(northernCell.easternCell, easternCell.northernCell);
    }

    /**
     * Creates and / or sets the northwestern cell.
     * <p>
     * <pre>
     * precondition:    both northernCell and westernCell are not null
     *                  either northernCell.westernCell or westernCell.northernCell is null
     * postcondition:   northernCell.westernCell == westernCell.northernCell
     * </pre>
     */
    private void createNorthwesternCell() {
        assert northernCell != null && westernCell != null && (northernCell.westernCell == null || westernCell
                .northernCell == null);

        if (northernCell != null && westernCell != null) {
            if (northernCell.westernCell != null) {
                westernCell.setNorthernCell(northernCell.westernCell);
            } else if (westernCell.northernCell != null) {
                northernCell.setWesternCell(westernCell.northernCell);
            } else {
                westernCell.setNorthernCell(new Cell(grid));
                northernCell.setWesternCell(westernCell.northernCell);
            }
        }

        assert Objects.equals(northernCell.westernCell, westernCell.northernCell);
    }

    /**
     * Creates and / or sets the southeastern cell.
     * <p>
     * <pre>
     * precondition:    both southernCell and easternCell are not null
     *                  either southernCell.easternCell or easternCell.southernCell is null
     * postcondition:   southernCell.easternCell == easternCell.southernCell
     * </pre>
     */
    private void createSoutheasternCell() {
        assert southernCell != null && easternCell != null && (southernCell.easternCell == null || easternCell
                .southernCell == null);

        if (southernCell != null && easternCell != null) {
            if (southernCell.easternCell != null) {
                easternCell.setSouthernCell(southernCell.easternCell);
            } else if (easternCell.southernCell != null) {
                southernCell.setEasternCell(easternCell.southernCell);
            } else {
                easternCell.setSouthernCell(new Cell(grid));
                southernCell.setEasternCell(easternCell.southernCell);
            }
        }

        assert Objects.equals(southernCell.easternCell, easternCell.southernCell);
    }

    /**
     * Creates the southern cell.
     * <p>
     * <pre>
     * precondition:    southernCell is null
     * postcondition:   southernCell.northernCell == this cell
     * </pre>
     */
    private void createSouthernCell() {
        assert southernCell == null;

        setSouthernCell(new Cell(grid));

        if (easternCell != null) {
            createSoutheasternCell();
        }

        if (westernCell != null) {
            createSouthwesternCell();
        }

        assert Objects.equals(southernCell.northernCell, this);
    }

    /**
     * Creates and / or sets the southwestern cell.
     * <p>
     * <pre>
     * precondition:    both southernCell and westernCell are not null
     *                  either southernCell.westernCell or westernCell.southernCell is null
     * postcondition:   southernCell.westernCell == westernCell.southernCell
     * </pre>
     */
    private void createSouthwesternCell() {
        assert southernCell != null && westernCell != null && (southernCell.westernCell == null || westernCell
                .southernCell == null);

        if (southernCell != null && westernCell != null) {
            if (southernCell.westernCell != null) {
                westernCell.setSouthernCell(southernCell.westernCell);
            } else if (westernCell.southernCell != null) {
                southernCell.setWesternCell(westernCell.southernCell);
            } else {
                westernCell.setSouthernCell(new Cell(grid));
                southernCell.setWesternCell(westernCell.southernCell);
            }
        }

        assert Objects.equals(southernCell.westernCell, westernCell.southernCell);
    }

    /**
     * Creates the western cell.
     * <p>
     * <pre>
     * precondition:    westernCell is null
     * postcondition:   westernCell.easternCell == this cell
     * </pre>
     */
    private void createWesternCell() {
        assert westernCell == null;

        setWesternCell(new Cell(grid));

        if (northernCell != null) {
            createNorthwesternCell();
        }

        if (southernCell != null) {
            createSouthwesternCell();
        }

        assert Objects.equals(westernCell.easternCell, this);
    }

    /**
     * Sets the eastern cell, then sets the eastern cell's western cell to this. This method should only be called if
     * eastern cell has not been set yet.
     * <p>
     * <pre>
     * precondition:    this.easternCell is null
     * postcondition:   this.easternCell.westernCell == this cell
     * </pre>
     *
     * @param easternCell
     */
    private void setEasternCell(Cell easternCell) {
        assert this.easternCell == null : "Eastern cell already set!";

        easternCell.westernCell = this;
        this.easternCell = easternCell;

        assert Objects.equals(this.easternCell.westernCell, this);
    }

    /**
     * Sets the northern cell, then sets the northern cell's southern cell to this. This method should only be called if
     * northern cell has not been set yet.
     * <p>
     * <pre>
     * precondition:    this.northernCell is null
     * postcondition:   this.northernCell.southernCell == this cell
     * </pre>
     *
     * @param northernCell
     */
    private void setNorthernCell(Cell northernCell) {
        assert this.northernCell == null : "Northern cell already set!";

        northernCell.southernCell = this;
        this.northernCell = northernCell;

        assert Objects.equals(this.northernCell.southernCell, this);
    }

    /**
     * Sets the southern cell, then sets the southern cell's northern cell to this. This method should only be called if
     * eastern cell has not been set yet.
     * <p>
     * <pre>
     * precondition:    this.southernCell is null
     * postcondition:   this.southernCell.northernCell == this cell
     * </pre>
     *
     * @param southernCell
     */
    private void setSouthernCell(Cell southernCell) {
        assert this.southernCell == null : "Southern cell already set!";

        southernCell.northernCell = this;
        this.southernCell = southernCell;

        assert Objects.equals(this.southernCell.northernCell, this);
    }

    /**
     * Sets the western cell, then sets the western cell's eastern cell to this. This method should only be called if
     * eastern cell has not been set yet.
     * <p>
     * <pre>
     * precondition:    this.easternCell is null
     * postcondition:   this.easternCell.westernCell == this cell
     * </pre>
     *
     * @param westernCell
     */
    private void setWesternCell(Cell westernCell) {
        assert this.westernCell == null : "Western cell already set!";

        westernCell.easternCell = this;
        this.westernCell = westernCell;

        assert Objects.equals(this.westernCell.easternCell, this);
    }

    /**
     * This hash code method simply calls the super method.
     *
     * @return the super hash code
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns true if the parameter is this cell. Cells are "equal" if and only if they are the same object, therefore
     * this method returns {@code this == o}.
     *
     * @param o another object
     *
     * @return true if the other object is this cell
     */
    @Override
    public boolean equals(Object o) {
        return this == o;
    }
}
