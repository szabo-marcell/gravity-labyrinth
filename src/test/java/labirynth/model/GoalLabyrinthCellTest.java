package labirynth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class GoalLabyrinthCellTest
{
    private GoalLabyrinthCell cell1;
    private GoalLabyrinthCell cell2;
    private GoalLabyrinthCell cell3;

    @BeforeEach
    void setUp() {
        cell1 = GoalLabyrinthCell.createCell(false, false, false, false);
        cell2 = GoalLabyrinthCell.createCell(false, false, true, true);
        cell3 = GoalLabyrinthCell.createCell(true, true, true, false);
    }

    @Test
    void createCell()
    {
        assertInstanceOf(GoalLabyrinthCell.class, cell1);
        assertInstanceOf(GoalLabyrinthCell.class, cell2);
        assertInstanceOf(GoalLabyrinthCell.class, cell3);
        assertInstanceOf(GravityLabyrinthCell.class, cell1);
    }

    @Test
    void createCellFlyweight()
    {
        assertSame(cell1, GoalLabyrinthCell.createCell(false, false, false, false));
        assertSame(cell2, GoalLabyrinthCell.createCell(false, false, true, true));
        assertSame(cell3, GoalLabyrinthCell.createCell(true, true, true, false));
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
        assertEquals(EnumSet.noneOf(Direction.class), cell1.getWalls());
        assertEquals(EnumSet.of(Direction.SOUTH, Direction.WEST), cell2.getWalls());
        assertEquals(EnumSet.of(Direction.SOUTH, Direction.NORTH, Direction.EAST), cell3.getWalls());
    }
}
