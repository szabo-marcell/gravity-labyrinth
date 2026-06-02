package labirynth.model;

import puzzle.State;
package
import java.util.Set;

public class LabirynthState implements State<Direction,LabirynthState> {
    private static final LabirynthCell[][] BOARD = {
            {4, 6, 2, 5, 1},
            {5, 2, 1, 0, 4},
            {6, 2, 3, 1, 5},
            {6, 4, 6, 3, 6},
            {5, 3, 4, 5, 1},
            {3, 6, 0, 3, 3}
    };
    @Override
    public boolean isSolved() {
        return false;
    }

    @Override
    public boolean isLegalMove(Direction move) {
        return false;
    }

    @Override
    public void makeMove(Direction move) {

    }

    @Override
    public Set<Direction> getLegalMoves() {
        return Set.of();
    }

    @Override
    public LabirynthState copy() {
        return null;
    }
}
