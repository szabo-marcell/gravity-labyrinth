package labirynth.model;

import puzzle.State;

import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import puzzle.solver.BreadthFirstSearch;

/**
 * Represents the state of the labyrinth puzzle.
 * <p>
 * Implements the {@link State} interface, which defines the operations required for
 * state-space search: querying legal moves, executing a move, and checking whether the
 * puzzle is solved. The board is described by a 7×7 matrix of {@link LabirynthCell} instances.
 */
public class LabirynthState implements State<Direction, LabirynthState> {

    /**
     * A map from cell-type name to {@link LabirynthCell} instance, covering all possible
     * wall configurations. The key encodes the present walls separated by hyphens
     * (e.g. {@code "top-right"}); the empty string ({@code ""}) denotes a cell with no walls.
     * Cells are shared via the Flyweight pattern through {@link LabirynthCell#createCell}.
     * The {@code "right-bottom-left"} entry is a {@link GoalLabyrinthCell} — the target cell.
     */
    public static final Map<String, LabirynthCell> cellmap = Map.ofEntries(
            Map.entry("",                   LabirynthCell.createCell(false, false, false, false)),
            Map.entry("top",                LabirynthCell.createCell(true,  false, false, false)),
            Map.entry("right",              LabirynthCell.createCell(false, true,  false, false)),
            Map.entry("bottom",             LabirynthCell.createCell(false, false, true,  false)),
            Map.entry("left",               LabirynthCell.createCell(false, false, false, true)),
            Map.entry("top-right",          LabirynthCell.createCell(true,  true,  false, false)),
            Map.entry("top-left",           LabirynthCell.createCell(true,  false, false, true)),
            Map.entry("top-bottom",         LabirynthCell.createCell(true,  false, true,  false)),
            Map.entry("right-bottom",       LabirynthCell.createCell(false, true,  true,  false)),
            Map.entry("right-left",         LabirynthCell.createCell(false, true,  false, true)),
            Map.entry("bottom-left",        LabirynthCell.createCell(false, false, true,  true)),
            Map.entry("top-right-left",     LabirynthCell.createCell(true,  true,  false, true)),
            Map.entry("top-right-bottom",   LabirynthCell.createCell(true,  true,  true,  false)),
            Map.entry("right-bottom-left-goal",  GoalLabyrinthCell.createCell(false, true, true, true)),
            Map.entry("right-bottom-left",  LabirynthCell.createCell(false, true, true, true))
    );

    /**
     * The 7×7 matrix of cells that makes up the labyrinth board.
     * Each element references a {@link LabirynthCell} instance from {@link #cellmap}.
     * Row and column indices start at (0, 0) in the top-left corner.
     */
    private static final LabirynthCell[][] BOARD = {
            {cellmap.get("top-right-left"), cellmap.get("top-left"),  cellmap.get("top-bottom"),        cellmap.get("top-right"),    cellmap.get("top-left"),    cellmap.get("top"),    cellmap.get("top-right-bottom") },
            {cellmap.get("left"),           cellmap.get(""),           cellmap.get("top"),               cellmap.get(""),             cellmap.get(""),            cellmap.get(""),       cellmap.get("top-right")        },
            {cellmap.get("left"),           cellmap.get("bottom"),     cellmap.get("right"),             cellmap.get("left"),         cellmap.get(""),            cellmap.get("right"),  cellmap.get("right-left")       },
            {cellmap.get("left"),           cellmap.get("top"),        cellmap.get(""),                  cellmap.get("right-bottom"), cellmap.get("right-left"),  cellmap.get("left"),   cellmap.get("right-bottom")     },
            {cellmap.get("bottom-left"),    cellmap.get(""),           cellmap.get(""),                  cellmap.get("top"),          cellmap.get("bottom"),      cellmap.get(""),       cellmap.get("top-right")        },
            {cellmap.get("top-left"),       cellmap.get("right"),      cellmap.get("right-bottom-left-goal"), cellmap.get("left"),         cellmap.get("top"),         cellmap.get(""),       cellmap.get("right")            },
            {cellmap.get("bottom-left"),    cellmap.get("bottom"),     cellmap.get("top-bottom"),        cellmap.get("right-bottom"), cellmap.get("bottom-left"), cellmap.get("right-bottom"), cellmap.get("right-bottom-left")}
    };

    /**
     * The number of rows in the board.
     */
    public static final int ROWS = BOARD.length;

    /**
     * The number of columns in the board.
     */
    public static final int COLS = BOARD[0].length;

    /**
     * The disk (ball) game piece currently on the board.
     */
    private final Disk disk;

    /**
     * The current position of the disk on the board.
     * Updated by {@link #makeMove(Direction)} as the disk slides.
     */
    private Position position;

    /**
     * Creates a new {@code LabirynthState} with the disk placed at the top-left corner (0, 0).
     */
    public LabirynthState()
    {
        this(1,4);
    }
    public LabirynthState(int posX, int posY)
    {
        this.disk= new Disk();
        this.position= new Position(posX,posY);
    }

    /**
     * Creates a new {@code LabirynthState} with the given disk placed at the given position.
     *
     * @param disk     the disk game piece
     * @param position the initial position of the disk on the board
     */
    public LabirynthState(Disk disk, Position position) {
        this.disk = disk;
        this.position = position;
    }

    /**
     * Returns whether the current state is solved, i.e. whether the disk has reached the goal cell.
     *
     * @return {@code true} if the disk is on the {@link GoalLabyrinthCell}; {@code false} otherwise
     */
    @Override
    public boolean isSolved() {
        return BOARD[position.row()][position.col()] instanceof GoalLabyrinthCell;
    }

    /**
     * Returns whether the given move is legal in the current state.
     * A move is legal if the current cell has no wall in that direction and
     * the adjacent cell in that direction is within the board bounds.
     *
     * @param move the direction to check
     * @return {@code true} if the move can be executed; {@code false} otherwise
     */
    @Override
    public boolean isLegalMove(Direction move) {
        if (BOARD[position.row()][position.col()].hasWall(move)) return false;
        int newRow = position.row() + move.getRowChange();
        int newCol = position.col() + move.getColChange();
        return newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS;
    }

    /**
     * Executes the given move on the current state.
     * The disk slides in the specified direction, advancing one cell at a time,
     * until it is stopped by a wall on the current cell or by the edge of the board.
     *
     * @param move the direction to move; must be a legal move
     */
    @Override
    public void makeMove(Direction move) {
        int row = position.row();
        int col = position.col();
        while (!BOARD[row][col].hasWall(move)) {
            int nextRow = row + move.getRowChange();
            int nextCol = col + move.getColChange();
            if (nextRow < 0 || nextRow >= ROWS || nextCol < 0 || nextCol >= COLS) break;
            row = nextRow;
            col = nextCol;
        }
        position = new Position(row, col);
    }

    /**
     * Returns the set of all legal moves available from the current state.
     *
     * @return the set of legal {@link Direction} values; an empty set if no move is possible
     */
    @Override
    public Set<Direction> getLegalMoves() {
        var legal = EnumSet.noneOf(Direction.class);
        for (var dir : Direction.values()) {
            if (isLegalMove(dir))
            {
                legal.add(dir);
            }
        }
        return legal;
    }

    /**
     * Returns a deep copy of the current state on which modifications do not affect the original.
     * Safe to share the same {@link Disk} and {@link Position} references because both are
     * effectively immutable within the context of a single state.
     *
     * @return an independent copy of the current state
     */
    @Override
    public LabirynthState copy() {
        return new LabirynthState(disk, position);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof LabirynthState that)
                && (disk.equals(that.disk))
                && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(disk, position);
    }

    @Override
    public String toString()
    {
        return String.format("LabirynthState[disk=%s,position=%s]", disk,position);
    }

    static void main()
    {
        var labirynth = new LabirynthState();
        new BreadthFirstSearch<Direction, LabirynthState>()
                .solveAndPrintSolution(labirynth);
    }
}
