package edu.wright.hendrix11.conway.logic;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Joe Hendrix
 */
public class Grid {

    private Set<Cell> aliveCells = new HashSet<>();
    private int generation = 0;

    /**
     * All cells in aliveCells must be alive. The current generation can never be negative.
     */
    private boolean classInv() {
        boolean invariant = true;

        for (Cell cell : aliveCells) {
            if (!cell.isAlive()) {
                invariant = false;
            }
        }

        if (generation < 0) {
            invariant = false;
        }

        return invariant;
    }

    /**
     * Empties the aliveCells set by toggling each living cell. The now-dead cell will remove itself from the aliveCell
     * set.
     * <p>
     * precondition:    aliveCells is not null postcondition:   aliveCells is empty and all cells that were in
     * aliveCells are not alive
     */
    public void clear() {
        // The copy prevents a java.util.ConcurrentModificationException
        Set<Cell> copy = new HashSet<>(aliveCells);
        copy.forEach(Cell::toggle);
        assert aliveCells.isEmpty();
        assert classInv();
    }

    /**
     * Returns the current generation.
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Ticks or cycles through one generation. All cells must be checked before they are toggled in order to prevent
     * them from affecting an adjacent cell's outcome.
     * <p>
     * precondition: postcondition:   the new generation is exactly one higher than the old generation any cells that
     * are now alive are in aliveCells any cells that are now dead are not in aliveCells
     */
    public void tick() {
        assert classInv();

        Set<Cell> toggleTheseCells = new HashSet<>();

        checkLivingCells(toggleTheseCells);
        checkAdjacentDeadCells(toggleTheseCells);

        toggleCells(toggleTheseCells);

        generation++;

        assert classInv();
    }

    private void checkAdjacentDeadCells(Set<Cell> toggleTheseCells) {
        aliveCells.forEach(cell -> cell.getNeighbors().forEach(neighbor -> {
            if (!neighbor.isAlive() && checkDeadCell(neighbor)) {
                toggleTheseCells.add(neighbor);
            }
        }));
    }

    /**
     * This method checks to see if a dead cell should become alive. Since it is checking a dead cell, rule 4 for the
     * Game of Life applies:
     * <p>
     * <ol start="4"> <li>Any dead cell with exactly three live neighbours becomes a live cell, as if by
     * reproduction.</li> </ol>
     * <p>
     * precondition:    deadCell is not alive postcondition:
     *
     * @return true if it should become alive, false otherwise
     */
    private boolean checkDeadCell(Cell deadCell) {
        assert !deadCell.isAlive();

        int numberOfLivingNeighbors = deadCell.getNumberLivingNeighbors();

        return numberOfLivingNeighbors == 3;
    }

    /**
     * This method checks to see if a living cell should die. Since it is checking a living cell, rules 1 through 3 for
     * the Game of Life apply:
     * <p>
     * <ol> <li>Any live cell with fewer than two live neighbours dies, as if caused by under-population.</li> <li>Any
     * live cell with two or three live neighbours lives on to the next generation.</li> <li>Any live cell with more
     * than three live neighbours dies, as if by over-population.</li> </ol>
     * <p>
     * precondition:    livingCell is alive postcondition:
     *
     * @return true if it should die, false otherwise
     */
    private boolean checkLivingCell(Cell livingCell) {
        assert livingCell.isAlive();

        int numberOfLivingNeighbors = livingCell.getNumberLivingNeighbors();

        return numberOfLivingNeighbors < 2 || numberOfLivingNeighbors > 3;
    }

    /**
     * precondition: postcondition:
     */
    private void checkLivingCells(Set<Cell> toggleTheseCells) {
        aliveCells.forEach(cell -> {
            if (checkLivingCell(cell)) {
                toggleTheseCells.add(cell);
            }
        });
    }

    private void toggleCells(Set<Cell> toggleTheseCells) {
        toggleTheseCells.forEach(Cell::toggle);
    }

    /**
     * Adds a living cell to the aliveCells set. This method is package-private and is called explicitly by the {@link
     * Cell} class.
     * <p>
     * precondition:    cell is alive postcondition:   cell is in aliveCells
     */
    void addLivingCell(Cell cell) {
        assert cell.isAlive();

        aliveCells.add(cell);

        assert aliveCells.contains(cell);
        assert classInv();
    }

    /**
     * Removes a dead cell from the aliveCells set. This method is package-private and is called explicitly by the {@link
     * Cell} class.
     * <p>
     * precondition:    cell is not alive postcondition:   cell is not in aliveCells
     */
    void removeDeadCell(Cell cell) {
        assert !cell.isAlive();

        aliveCells.remove(cell);

        assert !aliveCells.contains(cell);
        assert classInv();
    }
}
