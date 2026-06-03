package labirynth.model;

import common.util.board.RelativeDirection;

/**
 * Implements the {@link RelativeDirection} interface to provide row and column index changes
 * for grid-based navigation.
 */
public enum Direction implements RelativeDirection {

    /** North (up) direction; decreases the row index by 1. */
    NORTH,

    /** West (left) direction. */
    WEST,

    /** South (down) direction; increases the row index by 1. */
    SOUTH,

    /** East (right) direction. */
    EAST;

    /**
     * {@return the row index delta for this direction}
     */
    @Override
    public int getRowChange() {
        return switch (this) {
            case NORTH  -> -1;
            case SOUTH  ->  1;
            default     ->  0;
        };
    }

    /**
     * {@return the column index delta for this direction}
     */
    @Override
    public int getColChange() {
        return switch (this) {
            case WEST  ->  -1;
            case EAST  ->   1;
            default    ->  0;
        };
    }
}
