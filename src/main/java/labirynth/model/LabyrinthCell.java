package labirynth.model;

import java.util.*;

/**
 * Represents a single cell of the labyrinth, which may have walls on any of its four sides.
 * Applies the Flyweight design pattern: cells with the same wall configuration share a single
 * instance, ensured by the {@link #createCell(boolean, boolean, boolean, boolean)} factory method.
 */
public class LabyrinthCell {

    /**
     * Cache of already created cell instances for the Flyweight pattern implementation.
     * The key is a list of four booleans indicating wall presence in
     * {@code [top, right, bottom, left]} order.
     */
    private static final Map<List<Boolean>, LabyrinthCell> cells = new HashMap<>();

    /**
     * The set of directions on which this cell has a wall.
     * Only directions with an actual wall are included.
     */
    private final EnumSet<Direction> walls;


    /**
     * Factory method implementing the Flyweight design pattern.
     * Returns the existing instance if a cell with the given wall configuration already exists;
     * otherwise creates and stores a new one.
     *
     * @param wallTop    {@code true} if the cell has a top wall
     * @param wallRight  {@code true} if the cell has a right wall
     * @param wallBottom {@code true} if the cell has a bottom wall
     * @param wallLeft   {@code true} if the cell has a left wall
     * @return the {@code LabirynthCell} instance with the specified wall configuration
     */
    public static LabyrinthCell createCell(boolean wallTop, boolean wallRight, boolean wallBottom, boolean wallLeft) {
        var key = List.of(wallTop, wallRight, wallBottom, wallLeft);
        return cells.computeIfAbsent(key, k -> new LabyrinthCell(wallTop, wallRight, wallBottom, wallLeft));
    }

    /**
     * Constructs a new {@code LabirynthCell} instance with the given wall configuration.
     * Walls are stored as {@link Direction} values in the {@code walls} set.
     *
     * @param wallTop    {@code true} if the cell has a top ({@link Direction#NORTH}) wall
     * @param wallRight  {@code true} if the cell has a right wall
     * @param wallBottom {@code true} if the cell has a bottom ({@link Direction#SOUTH}) wall
     * @param wallLeft   {@code true} if the cell has a left wall
     */
    protected LabyrinthCell(boolean wallTop, boolean wallRight, boolean wallBottom, boolean wallLeft) {
        walls = EnumSet.noneOf(Direction.class);
        if (wallTop)    walls.add(Direction.NORTH);
        if (wallRight)  walls.add(Direction.EAST);
        if (wallBottom) walls.add(Direction.SOUTH);
        if (wallLeft)   walls.add(Direction.WEST);
    }

    /**
     * {@return {@code true} if there is a wall in the given direction; {@code false} otherwise}
     * @param direction the direction to check
     */
    public boolean hasWall(Direction direction) {
        return walls.contains(direction);
    }

    /**
     * Returns a copy of the set of directions on which this cell has a wall.
     * Modifying the returned set does not affect the internal state of this cell.
     * @return a copy of the wall directions; an empty set if there are no walls
     */
    public Set<Direction> getWalls() {
        return EnumSet.copyOf(walls.isEmpty() ? EnumSet.noneOf(Direction.class) : walls);
    }
}
