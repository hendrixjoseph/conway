package edu.wright.hendrix11.conway.pattern;

/**
 * @author Joe Hendrix
 */
public abstract class Pattern {
    public abstract String[] getPattern();

    public static final Single single = new Single();

    @Override
    public String toString() {
        return getClass().getSimpleName().toLowerCase();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o.getClass().equals(getClass());
    }

    private static class Single extends Pattern {

        @Override
        public String[] getPattern() {
            return new String[]{"X"};
        }
    }
}

