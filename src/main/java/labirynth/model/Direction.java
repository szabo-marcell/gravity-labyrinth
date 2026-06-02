package labirynth.model;

import common.util.board.RelativeDirection;

public enum Direction implements RelativeDirection {

    NORTH, WEST, SOUTH, EAST;

    @Override
    public int getRowChange() {
        return switch (this) {
            case NORTH   -> -1;
            case SOUTH  ->  1;
            default     ->  0;
        };
    }

    @Override
    public int getColChange() {
        return switch (this) {
            case WEST ->  1;
            case EAST  -> -1;
            default    ->  0;
        };
    }
}
