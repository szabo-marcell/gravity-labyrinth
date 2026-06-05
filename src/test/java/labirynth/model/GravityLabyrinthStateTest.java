package labirynth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GravityLabyrinthStateTest {
    private GravityLabyrinthState state1;
    private GravityLabyrinthState state2;
    private GravityLabyrinthState state3;

    @BeforeEach
    void setUp() {
        state1 = new GravityLabyrinthState();
        state2 = new GravityLabyrinthState(5,2);
        state3 = new GravityLabyrinthState(0,2);
    }

    @Test
    void isSolved() {
        assertFalse(state1.isSolved());
        assertTrue(state2.isSolved());
        assertFalse(state3.isSolved());
    }
    @Test
    void isLegalMove() {
        assertTrue(state1.isLegalMove(Direction.NORTH));
        assertTrue(state1.isLegalMove(Direction.WEST));
        assertTrue(state3.isLegalMove(Direction.EAST));
        assertFalse(state2.isLegalMove(Direction.SOUTH));
        assertFalse(state2.isLegalMove(Direction.WEST));
        assertFalse(state3.isLegalMove(Direction.SOUTH));
    }
}
