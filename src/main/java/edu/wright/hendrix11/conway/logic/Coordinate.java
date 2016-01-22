package edu.wright.hendrix11.conway.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Joe Hendrix
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Coordinate> getNeighbors() {
        List<Coordinate> neighbors = new ArrayList<>();

        for(int i = x - 1; x <= x + 1; x++) {
            for(int j = y - 1; y <= y + 1; y++) {
                if(i != x && j != y) {
                    neighbors.add(new Coordinate(i,j));
                }
            }
        }

        assert neighbors.size() == 8;
        assert !neighbors.contains(this);

        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Coordinate that = (Coordinate) o;

        return x == that.getX() &&  y == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
