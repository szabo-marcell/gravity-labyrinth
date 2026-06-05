package labirynth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    void makeMoveTest_state1()
    {
        state1.makeMove(Direction.EAST);
        assertEquals(new Position(1,6),state1.getPosition());
        state1.makeMove(Direction.SOUTH);
        assertEquals(new Position(3,6),state1.getPosition());
        state1.makeMove(Direction.WEST);
        assertEquals(new Position(3,5),state1.getPosition());
    }
    @Test
    void makeMoveTest_state3()
    {
        state3.makeMove(Direction.WEST);
        assertEquals(new Position(0,1),state3.getPosition());
        state3.makeMove(Direction.SOUTH);
        assertEquals(new Position(2,1),state3.getPosition());
    }
    @Test
    void getLegalMoves() {
        assertEquals(EnumSet.allOf(Direction.class), state1.getLegalMoves());
        assertEquals(EnumSet.of(Direction.NORTH), state2.getLegalMoves());
        assertEquals(EnumSet.of(Direction.EAST, Direction.WEST), state3.getLegalMoves());
    }
    @Test
    void testEquals() {
        assertTrue(state1.equals(state1));
        assertTrue(state1.equals(new GravityLabyrinthState()));
        assertTrue(state1.equals(new GravityLabyrinthState(1,4)));
        assertTrue(state2.equals(new GravityLabyrinthState(5,2)));
        assertFalse(state3.equals(new GravityLabyrinthState(5,2)));
        assertFalse(state1.equals(null));
        assertFalse(state1.equals("Hello, World!"));
        assertFalse(state1.equals(state2));
        assertFalse(state1.equals(state3));
    }
    @Test
    void testHashCode() {
        assertEquals(state1.hashCode(), state1.hashCode());
        assertEquals(state1.hashCode(), state1.copy().hashCode());
    }

    @Test
    void copy() {
        var copy = state1.copy();
        assertEquals(state1, copy);
        assertNotSame(state1, copy);
    }
    @Test
    void testToString() {
        assertEquals("GravityLabyrinthState[position=Position[row=5, col=2]]", state2.toString());
        assertEquals("GravityLabyrinthState[position=Position[row=0, col=2]]", state3.toString());
    }


}
