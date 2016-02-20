package edu.wright.hendrix11.conway.gui;

import javafx.scene.layout.Pane;

import java.util.function.Supplier;

import edu.wright.hendrix11.conway.logic.Cell;
import edu.wright.hendrix11.conway.pattern.Generator;
import edu.wright.hendrix11.conway.pattern.Pattern;
import edu.wright.hendrix11.conway.pattern.StillLifePattern;

/**
 * @author Joe Hendrix
 */
public class CellPane extends Pane {

    private static final String DEAD_CELL_STYLE_CLASS = "dead-cell";
    private static final String LIVING_CELL_STYLE_CLASS = "living-cell";

    private final Cell cell;
    private Supplier<Pattern> patternSupplier;

    public CellPane(Cell cell, Supplier<Pattern> stringSupplier) {
        this.cell = cell;
        this.patternSupplier = stringSupplier;
        setPrefSize(100.0f, 100.0f);
        getStyleClass().add("cell");
        setStyleClass();
        setOnMouseClicked(event -> clicked());
        cell.onToggle(event -> setStyleClass());
        assert classInv();
    }

    private void clicked() {
        Pattern pattern = patternSupplier.get();
        Generator.generate(cell, pattern.getPattern());
    }

    private boolean classInv() {
        return (cell.isAlive() && getStyleClass().contains(LIVING_CELL_STYLE_CLASS) && !getStyleClass().contains
                (DEAD_CELL_STYLE_CLASS)) || ((!cell.isAlive() && !getStyleClass().contains(LIVING_CELL_STYLE_CLASS)
                && getStyleClass().contains(DEAD_CELL_STYLE_CLASS)));
    }

    public Cell getCell() {
        return cell;
    }

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
