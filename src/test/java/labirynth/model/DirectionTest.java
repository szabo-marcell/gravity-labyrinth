package labirynth.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest
{
    @Test
    void values()
    {
        assertEquals(4, Direction.values().length);
        assertEquals(Direction.NORTH, Direction.valueOf("NORTH"));
        assertEquals(Direction.WEST, Direction.valueOf("WEST"));
        assertEquals(Direction.SOUTH, Direction.valueOf("SOUTH"));
        assertEquals(Direction.EAST, Direction.valueOf("EAST"));
    }

    @Test
    void getRowChange()
    {
        assertEquals(-1, Direction.NORTH.getRowChange());
        assertEquals(1, Direction.SOUTH.getRowChange());
        assertEquals(0, Direction.WEST.getRowChange());
        assertEquals(0, Direction.EAST.getRowChange());
    }

    @Test
    void getColChange()
    {
        assertEquals(0, Direction.NORTH.getColChange());
        assertEquals(0, Direction.SOUTH.getColChange());
        assertEquals(-1, Direction.WEST.getColChange());
        assertEquals(1, Direction.EAST.getColChange());
    }
}
