package labirynth.model;

/**
 * Represents the goal cell of the labyrinth.
 * <p>
 * Extends {@link LabirynthCell} and marks the cell that the ball must reach
 * to solve the puzzle. The wall configuration works identically to the base cell.
 */
public class GoalLabirynthCell extends LabirynthCell {

    /**
     * Constructs a new {@code GoalLabirynthCell} instance with the given wall configuration.
     *
     * @param wallTop    {@code true} if the cell has a top wall
     * @param wallRight  {@code true} if the cell has a right wall
     * @param wallBottom {@code true} if the cell has a bottom wall
     * @param wallLeft   {@code true} if the cell has a left wall
     */
    public GoalLabirynthCell(boolean wallTop, boolean wallRight, boolean wallBottom, boolean wallLeft) {
         super(wallTop,wallRight, wallBottom, wallLeft);
    }
}
