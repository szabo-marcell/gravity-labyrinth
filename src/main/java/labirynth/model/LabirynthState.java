package labirynth.model;

import puzzle.State;

import java.util.Map;
import java.util.Set;

public class LabirynthState implements State<Direction,LabirynthState> {

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
            Map.entry("right-bottom-left",  LabirynthCell.createCell(false, true,  true,  true))
    );

    private static final LabirynthCell[][] BOARD = {
            {cellmap.get("top-right-left"), cellmap.get("top-left"),  cellmap.get("top-bottom"),        cellmap.get("top-right"),    cellmap.get("top-left"),    cellmap.get("top"),    cellmap.get("top-right-bottom") },
            {cellmap.get("left"),           cellmap.get(""),           cellmap.get("top"),               cellmap.get(""),             cellmap.get(""),            cellmap.get(""),       cellmap.get("top-right")        },
            {cellmap.get("left"),           cellmap.get("bottom"),     cellmap.get("right"),             cellmap.get("left"),         cellmap.get(""),            cellmap.get("right"),  cellmap.get("right-left")       },
            {cellmap.get("left"),           cellmap.get("top"),        cellmap.get(""),                  cellmap.get("right-bottom"), cellmap.get("right-left"),  cellmap.get("left"),   cellmap.get("right-bottom")     },
            {cellmap.get("bottom-left"),    cellmap.get(""),           cellmap.get(""),                  cellmap.get("top"),          cellmap.get("bottom"),      cellmap.get(""),       cellmap.get("top-right")        },
            {cellmap.get("top-left"),       cellmap.get("right"),      cellmap.get("right-bottom-left"), cellmap.get("left"),         cellmap.get("top"),         cellmap.get(""),       cellmap.get("right")            },
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
    @Override
    public boolean isSolved() {
        return false;
    }

    @Override
    public boolean isLegalMove(Direction move) {
        return false;
    }

    @Override
    public void makeMove(Direction move) {

    }

    @Override
    public Set<Direction> getLegalMoves() {
        return Set.of();
    }

    @Override
    public LabirynthState copy() {
        return null;
    }
}
