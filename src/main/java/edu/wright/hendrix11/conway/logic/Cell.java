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
     * Creates the eastern cell.
     * 
     * precondition:    easternCell is null
     * postcondition:   easternCell.westernCell == this cell
     *
     * @return the eastern cell
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
     * 
     * precondition:    northernCell or easternCell is not null
     * postcondition:   northernCell.easternCell == easternCell.northernCell
     *
     * @return the eastern cell
     */
    private void createNortheasternCell() {
        assert northernCell != null || easternCell != null;
        
        if (northernCell != null && northernCell.easternCell != null) {
            easternCell.setNorthernCell(northernCell.easternCell);
        } else if (easternCell != null && easternCell.northernCell != null) {
            northernCell.setEasternCell(easternCell.northernCell);
        } else if (northernCell != null && easternCell != null) {
            easternCell.setNorthernCell(new Cell(grid));
            northernCell.setEasternCell(easternCell.northernCell);
        }
            
        assert Objects.equals(northernCell.easternCell, easternCell.northernCell);
    }
    
    /**
     * Creates and / or sets the northwestern cell.
     * 
     * precondition:    northernCell or westernCell is not null
     * postcondition:   northernCell.westernCell == westernCell.northernCell
     *
     * @return the eastern cell
     */
    private void createNorthwesternCell() {
        assert northernCell != null || westernCell != null;
        
        if (northernCell != null && northernCell.westernCell != null) {
            westernCell.setNorthernCell(northernCell.westernCell);
        } else if (westernCell != null && westernCell.northernCell != null) {
            northernCell.setWesternCell(westernCell.northernCell);
        } else {
            westernCell.setNorthernCell(new Cell(grid));
            northernCell.setWesternCell(westernCell.northernCell);
        }
            
        assert Objects.equals(northernCell.westernCell, westernCell.northernCell);
    }

    /**
     * Creates and / or sets the southeastern cell.
     * 
     * precondition:    southernCell or easternCell is not null
     * postcondition:   southernCell.easternCell == easternCell.southernCell
     *
     * @return the eastern cell
     */
    private void createSoutheasternCell() {
        assert southernCell != null || easternCell != null;
        
        if (southernCell != null && southernCell.easternCell != null) {
            easternCell.setSouthernCell(southernCell.easternCell);
        } else if (easternCell != null && easternCell.southernCell != null) {
            southernCell.setEasternCell(easternCell.southernCell);
        } else {
            easternCell.setSouthernCell(new Cell(grid));
            southernCell.setEasternCell(easternCell.southernCell);
        }
            
        assert Objects.equals(southernCell.easternCell, easternCell.southernCell);
    }
    
    /**
     * Creates and / or sets the southwestern cell.
     * 
     * precondition:    southernCell or westernCell is not null
     * postcondition:   southernCell.westernCell == westernCell.southernCell
     * 
     */
    private void createSouthwesternCell() {
        assert southernCell != null || westernCell != null;
        
        if (southernCell != null && southernCell.westernCell != null) {
            westernCell.setSouthernCell(southernCell.westernCell);
        } else if (westernCell != null && westernCell.southernCell != null) {
            southernCell.setWesternCell(westernCell.southernCell);
        } else {
            westernCell.setSouthernCell(new Cell(grid));
            southernCell.setWesternCell(westernCell.southernCell);
        }
            
        assert Objects.equals(southernCell.westernCell, westernCell.southernCell);
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
     * Creates the southern cell.
     * 
     * precondition:    southernCell is null
     * postcondition:   southernCell.northernCell == this cell
     * 
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
     * Creates the western cell.
     * 
     * precondition:    westernCell is null
     * postcondition:   westernCell.easternCell == this cell
     * 
     */      
    private void createWesternCell() {
        assert westernCell == null;
        
        setWesternCell(new Cell(grid));

        if (northernCell != null) {
            createNorthwesternCell()
        }

        if (southernCell != null) {
            createSouthwesternCell();
        }
        
        assert Objects.equals(westernCell.easternCell, this);
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
     * 
     * precondition:    
     * postcondition:   
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

    /**
     * Counts and returns the number of living neighbors. Since every cell has exactly eight neighbors, it can have at
     * most 8 living neighbors. No neighbor is required to be alive, however, so it can also have as few as 0 living
     * neighbors.
     * 
     * precondition:    
     * postcondition:   
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
     *
     *
     * @param toggleHandler
     */
    public void onToggle(Consumer<Void> toggleHandler) {
        this.toggleHandler = toggleHandler;
    }

    /**
     * Toggles the living state of this cell. If the cell was alive, after this method it is now dead. Conversely,
     * if the cell was dead, it is now alive.
     *
     * This method will also add or remove the cell from the living cell list in the {@link Grid} it is associated with,
     * depending if it is now alive or dead.
     * 
     * precondition:    
     * postcondition:   
     *
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
     * Sets the eastern cell, then sets the eastern cell's western cell to this. This method should only be called if
     * eastern cell has not been set yet.
     * 
     * precondition:    this.easternCell has not been set
     * postcondition:   this.easternCell.westernCell is this cell
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
     * 
     * precondition:    this.northernCell has not been set
     * postcondition:   this.northernCell.southernCell is this cell
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
     * 
     * precondition:    this.southernCell has not been set
     * postcondition:   this.southernCell.northernCell is this cell
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
     * 
     * precondition:    this.easternCell has not been set
     * postcondition:   this.easternCell.westernCell is this cell
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
