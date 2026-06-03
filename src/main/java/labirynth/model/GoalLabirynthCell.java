package labirynth.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the goal cell of the labyrinth.
 * <p>
 * Extends {@link LabirynthCell} and marks the cell that the ball must reach
 * to solve the puzzle. The wall configuration works identically to the base cell.
 * <p>
 * Applies the Flyweight pattern via {@link #createCell} so that only one instance
 * exists per wall configuration, consistent with {@link LabirynthCell}.
 */
public class GoalLabirynthCell extends LabirynthCell {

    /**
     * Cache of already created goal-cell instances for the Flyweight pattern.
     * Kept separate from {@link LabirynthCell}'s cache so that the goal cell
     * retains its distinct type at runtime.
     */
    private static final Map<List<Boolean>, GoalLabirynthCell> cells = new HashMap<>();

    /**
     * Factory method implementing the Flyweight pattern for goal cells.
     * Returns the existing instance if a goal cell with the given wall configuration already
     * exists; otherwise creates and stores a new one.
     *
     * @param wallTop    {@code true} if the cell has a top wall
     * @param wallRight  {@code true} if the cell has a right wall
     * @param wallBottom {@code true} if the cell has a bottom wall
     * @param wallLeft   {@code true} if the cell has a left wall
     * @return the {@code GoalLabirynthCell} instance with the specified wall configuration
     */
    public static GoalLabirynthCell createCell(boolean wallTop, boolean wallRight, boolean wallBottom, boolean wallLeft) {
        var key = List.of(wallTop, wallRight, wallBottom, wallLeft);
        return cells.computeIfAbsent(key, k -> new GoalLabirynthCell(wallTop, wallRight, wallBottom, wallLeft));
    }

    /**
     * Constructs a new {@code GoalLabirynthCell} instance with the given wall configuration.
     *
     * @param wallTop    {@code true} if the cell has a top wall
     * @param wallRight  {@code true} if the cell has a right wall
     * @param wallBottom {@code true} if the cell has a bottom wall
     * @param wallLeft   {@code true} if the cell has a left wall
     */
    protected GoalLabirynthCell(boolean wallTop, boolean wallRight, boolean wallBottom, boolean wallLeft) {
        super(wallTop, wallRight, wallBottom, wallLeft);
    }
}
