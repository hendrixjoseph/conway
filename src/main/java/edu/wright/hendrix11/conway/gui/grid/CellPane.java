package edu.wright.hendrix11.conway.gui.grid;

import javafx.scene.layout.Pane;

import java.util.function.Supplier;

import edu.wright.hendrix11.conway.logic.Cell;
import edu.wright.hendrix11.conway.logic.Pattern;

/**
 * @author Joe Hendrix
 */
public class CellPane extends Pane {

    private static final String DEAD_CELL_STYLE_CLASS = "dead-cell";
    private static final String LIVING_CELL_STYLE_CLASS = "living-cell";

    private final Cell cell;
    private Supplier<Pattern> patternSupplier;

    /**
     * @param cell the cell this object owns
     * @param patternSupplier a method that takes a cell as a parameter
     */
    public CellPane(Cell cell, Supplier<Pattern> patternSupplier) {
        this.cell = cell;
        this.patternSupplier = patternSupplier;
        setPrefSize(100.0f, 100.0f);
        getStyleClass().add("cell");
        setStyleClass();
        setOnMouseClicked(event -> clicked());
        cell.onToggle(event -> setStyleClass());
        assert classInv();
    }

    /**
     * If the cell this class has is alive, then the style class is the living cell style class and not the dead style
     * class. If the cell this class is not alive, then it is the other way around.
     *
     * @return true if the class satisfies its general contract
     */
    private boolean classInv() {
        return (cell.isAlive() && getStyleClass().contains(LIVING_CELL_STYLE_CLASS) && !getStyleClass().contains
                (DEAD_CELL_STYLE_CLASS)) || ((!cell.isAlive() && !getStyleClass().contains(LIVING_CELL_STYLE_CLASS)
                && getStyleClass().contains(DEAD_CELL_STYLE_CLASS)));
    }

    /**
     * This is the method called when this object is clicked.
     */
    private void clicked() {
        patternSupplier.get().generate(cell);
    }

    /**
     * This method is called whenever the cell this object owns is toggled. It ensures this object satisfies its
     * general contract.
     *
     * @see #classInv()
     */
    private void setStyleClass() {
        if (cell.isAlive()) {
            getStyleClass().remove(DEAD_CELL_STYLE_CLASS);
            getStyleClass().add(LIVING_CELL_STYLE_CLASS);
        } else {
            assert !cell.isAlive();
            getStyleClass().remove(LIVING_CELL_STYLE_CLASS);
            getStyleClass().add(DEAD_CELL_STYLE_CLASS);
        }

        assert classInv();
    }
}
