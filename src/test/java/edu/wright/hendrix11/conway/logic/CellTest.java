package edu.wright.hendrix11.conway.logic;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joe Hendrix
 */
public class CellTest {

    private Cell cell;

    @Before
    public void before() {
        if(cell == null) {
            cell = new Cell(new Grid());
        }
    }

    @Test
    public void testGetNeighbors() throws Exception {
        cell.getNeighbors();
    }

    @Test
    public void testGetNorthernCell() throws Exception {
        cell.getNorthernCell();
    }

    @Test
    public void testGetSouthernCell() throws Exception {
        cell.getSouthernCell();
    }

    @Test
    public void testGetEasternCell() throws Exception {
        cell.getEasternCell();
    }

    @Test
    public void testGetWesternCell() throws Exception {
        assert cell.getWesternCell() != null;
    }
}