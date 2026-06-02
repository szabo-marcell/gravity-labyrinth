package labirynth.model;

import common.util.board.RelativeDirection;

public enum Direction implements RelativeDirection {

    @Override
    public int getRowChange() {
        return 0;
    }

    @Override
    public int getColChange() {
        return 0;
    }
}
