package labirynth.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the goal cell of the labyrinth.
 * Extends {@link GravityLabyrinthCell} and marks the cell that the ball must reach
 * to solve the puzzle. The wall configuration works identically to the base cell.
 * Applies the Flyweight pattern via {@link #createCell} so that only one instance
 * exists per wall configuration, consistent with {@link GravityLabyrinthCell}.
 */
public class GoalLabyrinthCell extends GravityLabyrinthCell {

    /**
     * Cache of already created goal-cell instances for the Flyweight pattern.
     */
    private static final Map<List<Boolean>, GoalLabyrinthCell> cells = new HashMap<>();

    /**
     * Factory method implementing the Flyweight pattern for goal cells.
     *
     * @param wallTop    {@code true} if the cell has a top wall
     * @param wallRight  {@code true} if the cell has a right wall
     * @param wallBottom {@code true} if the cell has a bottom wall
     * @param wallLeft   {@code true} if the cell has a left wall
     * @return the {@code GoalLabirynthCell} instance with the specified wall configuration
     */
    public static GoalLabyrinthCell createCell(boolean wallTop, boolean wallRight, boolean wallBottom, boolean wallLeft) {
        var key = List.of(wallTop, wallRight, wallBottom, wallLeft);
        return cells.computeIfAbsent(key, k -> new GoalLabyrinthCell(wallTop, wallRight, wallBottom, wallLeft));
    }

    /**
     * Constructs a new {@code GoalLabirynthCell} instance with the given wall configuration.
     * It is the constructor of the class.
     * @param wallTop    {@code true} if the cell has a top wall
     * @param wallRight  {@code true} if the cell has a right wall
     * @param wallBottom {@code true} if the cell has a bottom wall
     * @param wallLeft   {@code true} if the cell has a left wall
     */
    protected GoalLabyrinthCell(boolean wallTop, boolean wallRight, boolean wallBottom, boolean wallLeft) {
        super(wallTop, wallRight, wallBottom, wallLeft);
    }
}
