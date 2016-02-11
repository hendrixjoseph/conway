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

    public Set<Cell> getNeighbors() {
        Set<Cell> neighbors = new HashSet<>();
        
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
    
    public Cell getNorthEasternCell() {
        Cell northEasternCell = null;
        
        if(northernCell != null) {
            northEasternCell = northernCell.easternCell;
        } else if(easternCell != null) {
            northEasternCell = easternCell.northernCell;
        }
        
        return northEasternCell;
    }
    
    public Cell getNorthWesternCell() {
        Cell northWesternCell = null;
        
        if(northernCell != null) {
            northWesternCell = northernCell.westernCell;
        } else if(westernCell != null) {
            northWesternCell = westernCell.northernCell;
        }
        
        return northWesternCell;
    }
    
    public Cell getSouthEasternCell() {
        Cell southEasternCell = null;
        
        if(southernCell != null) {
            southEasternCell = southernCell.easternCell;
        } else if(easternCell != null) {
            southEasternCell = easternCell.southernCell;
        }
        
        return southEasternCell;
    }
    
    public Cell getSouthWesternCell() {
        Cell southWesternCell = null;
        
        if(southernCell != null) {
            southWesternCell = southernCell.westernCell;
        } else if(westernCell != null) {
            southWesternCell = westernCell.southernCell;
        }
        
        return southWesternCell;
    }

    public void toggle() {
        alive = !alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getNumberLivingNeighbors() {
        int count = 0;
        
        for(Cell neighbor : getNeighbors()) {
            if(neighbor != null && neighbor.isAlive()) {
                count++;
            }
        }
        
        assert count >= 0 && count <= 8;
        
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
