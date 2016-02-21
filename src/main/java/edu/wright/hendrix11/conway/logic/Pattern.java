package edu.wright.hendrix11.conway.logic;

import java.util.Arrays;
import java.util.List;

/**
 * @author Joe Hendrix
 */
public class Pattern {

    public static final Pattern block = new Pattern("block", new String[]{"XX", "XX"});
    public static final Pattern single = new Pattern("single", new String[]{"X"});

    private final String name;
    private final List<String> pattern;

    public Pattern(String name, List<String> pattern) {
        this.name = name;
        this.pattern = pattern;
        assert classInv();
    }

    public Pattern(String name, String[] pattern) {
        this(name, Arrays.asList(pattern));
    }

    private boolean classInv() {
        boolean invarient = true;

        invarient = name != null && pattern != null;

        if (pattern != null) {
            for (String string : pattern) {
                if (string == null) {
                    invarient = false;
                }
            }
        }

        return invarient;
    }

    public void generate(Cell seed) {
        Cell outterCell = seed;

        for (String row : pattern) {

            Cell innerCell = outterCell;

            for (char letter : row.toCharArray()) {
                if (letter == 'X') {
                    innerCell.toggle();
                }

                innerCell = innerCell.getEasternCell();
            }

            outterCell = outterCell.getSouthernCell();
        }

        assert classInv();
    }

    @Override
    public String toString() {
        return name;
    }
}

