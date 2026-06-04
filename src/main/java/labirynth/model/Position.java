package labirynth.model;

/**
 * Represents an immutable position on the labyrinth board as a (row, column) pair.
 * Row 0, column 0 is the top-left corner; rows increase downward, columns increase rightward.
 *
 * @param row the zero-based row index
 * @param col the zero-based column index
 */
public record Position(int row, int col) {}
