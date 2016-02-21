package edu.wright.hendrix11.conway.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Joe Hendrix
 */
public class Pattern {

    public static final Pattern block = new Pattern("block", new String[]{"XX", "XX"});
    public static final Pattern single = new Pattern("single", new String[]{"X"});

    private String name;
    private List<String> pattern;

    public Pattern(String name, List<String> pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    public Pattern(String name, String[] pattern) {
        this(name, Arrays.asList(pattern));
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
    }

    @Override
    public String toString() {
        return name;
    }
}

