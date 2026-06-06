package labirynth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static labirynth.model.GravityLabyrinthCell.createCell;
import static org.junit.jupiter.api.Assertions.*;

public class GravityLabyrinthCellTest
{
    private GravityLabyrinthCell cell1;
    private GravityLabyrinthCell cell2;
    private GravityLabyrinthCell cell3;

    @BeforeEach
    void setUp() {
        cell1 = GravityLabyrinthCell.createCell(false, false, false, false);
        cell2 = GravityLabyrinthCell.createCell(false, false, true, true);
        cell3 = GravityLabyrinthCell.createCell(true, true, true, false);
    }
    @Test
    void createCell()
    {
        assertInstanceOf(GravityLabyrinthCell.class, cell1);
        assertInstanceOf(GravityLabyrinthCell.class, cell2);
        assertInstanceOf(GravityLabyrinthCell.class, cell3);
    }
    @Test
    void hasWall()
    {
        assertFalse(cell1.hasWall(Direction.NORTH));
        assertFalse(cell1.hasWall(Direction.SOUTH));
        assertFalse(cell2.hasWall(Direction.NORTH));
        assertFalse(cell2.hasWall(Direction.EAST));
        assertTrue(cell2.hasWall(Direction.SOUTH));
        assertTrue(cell3.hasWall(Direction.NORTH));
        assertTrue(cell3.hasWall(Direction.EAST));
        assertFalse(cell3.hasWall(Direction.WEST));
    }
    @Test
    void isDeadEnd()
    {
        assertTrue(cell3.isDeadEnd());
        assertFalse(cell2.isDeadEnd());
        assertFalse(cell1.isDeadEnd());
    }
    @Test
    void getWalls()
    {
        assertEquals(EnumSet.noneOf(Direction.class),cell1.getWalls());
        assertEquals(EnumSet.of(Direction.SOUTH, Direction.WEST),cell2.getWalls());
        assertEquals(EnumSet.of(Direction.SOUTH,Direction.NORTH, Direction.EAST),cell3.getWalls());

    }
}
