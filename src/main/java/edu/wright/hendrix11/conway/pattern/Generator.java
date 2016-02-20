package edu.wright.hendrix11.conway.pattern;

import edu.wright.hendrix11.conway.logic.Cell;

/**
 * @author Joe Hendrix
 */
public class Generator {
    public static void generate(Cell seed, String[] pattern) {
        Cell outterCell = seed;

        for(String row : pattern) {

            Cell innerCell = outterCell;

            for(char letter : row.toCharArray()) {
                if(letter == 'X') {
                    innerCell.toggle();
                }

                innerCell = innerCell.getEasternCell();
            }

            outterCell = outterCell.getSouthernCell();
        }
    }
}
