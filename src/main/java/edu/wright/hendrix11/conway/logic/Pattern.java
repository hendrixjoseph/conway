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

    /**
     * The name is not null, the pattern list is not null, and no element in the pattern list is null.
     */
    private boolean classInv() {
        boolean invarient = name != null && pattern != null;

        if (pattern != null) {
            for (String string : pattern) {
                if (string == null) {
                    invarient = false;
                }
            }
        }

        return invarient;
    }

    /**
     * Generates the pattern specified in pattern.
     * <p>
     * <pre>precondition:    seed is not null
     * postcondition:</pre>
     */
    public void generate(Cell seed) {
        assert seed != null;

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

    /**
     * The string representation of this pattern, which is its name.
     */ 
    @Override
    public String toString() {
        return name;
    }
}

