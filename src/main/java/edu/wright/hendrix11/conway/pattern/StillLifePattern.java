package edu.wright.hendrix11.conway.pattern;

import javafx.scene.layout.Pane;

import edu.wright.hendrix11.conway.logic.Cell;

/**
 * @author Joe Hendrix
 */
public class StillLifePattern {

    public static final String[] block = {"XX",
                                          "XX"};

    public static final String[] beehive = {".XX.",
                                             "X..X",
                                             ".XX."};

    public static class Block extends Pattern {

        @Override
        public String[] getPattern() {
            return block;
        }
    }

    public static class Behive extends Pattern {

        @Override
        public String[] getPattern() {
            return beehive;
        }
    }
}
