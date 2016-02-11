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
        
        count += countCardinalLivingNeighbors();
        count += countDiagonalLivingNeighbors();
        
        return count;
    }
    
    private int countNorthernDiagonalLivingNeighbors() {
        int count = 0;
        
        if(northernCell != null) {
            
            if(northernCell.easternCell != null && northernCell.easternCell.isAlive()) {
                count++;
            }
            
            if(northernCell.westernCell != null && northernCell.westernCell.isAlive()) {
                count++;
            }
            
        } else {
            
            if(easternCell != null && easternCell.northernCell != null && easternCell.northernCell.isAlive()) {
                count++;
            }
            
            if(westernCell != null && westernCell.northernCell != null && westernCell.northernCell.isAlive()) {
                count++;
            }
        }
        
        return count;
    }
    
    private int countSouthernDiagonalLivingNeighbors() {
        int count = 0;
        
        if(southernCell != null) {
            
            if(southernCell.easternCell != null && southernCell.easternCell.isAlive()) {
                count++;
            }
            
            if(southernCell.westernCell != null && southernCell.westernCell.isAlive()) {
                count++;
            }
            
        } else {
            
            if(easternCell != null && easternCell.southernCell != null && easternCell.southernCell.isAlive()) {
                count++;
            }
            
            if(westernCell != null && westernCell.southernCell != null && westernCell.southernCell.isAlive()) {
                count++;
            }
        }
        
        return count;
    }
    
    private int countDiagonalLivingNeighbors() {
        int count = 0;
        
        count += countNorthernDiagonalLivingNeighbors;
        count += countSouthernDiagonalLivingNeighbors;
        
        return count;
    }
    
    private int countCardinalLivingNeighbors() {
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
