package labirynth.gui;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import labirynth.model.*;
import org.pmw.tinylog.Logger;


/**
 * JavaFX controller for the main game screen.
 * <p>
 * Manages the labyrinth board rendering, disk movement, move counter,
 * keyboard accelerators, and the reset/exit actions.
 */
public class GravityLabyrinthController {

    @FXML
    private GridPane board;

    @FXML
    private Button downButton;

    @FXML
    private Button upButton;

    @FXML
    private Button rightButton;

    @FXML
    private Button leftButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField numberOfMovesField;

    @FXML
    private Label numberOfMovesLabel;

    private LabirynthState state;

    private Circle diskCircle;

    private final IntegerProperty numberOfMoves = new SimpleIntegerProperty();

    private final StringProperty playerName = new SimpleStringProperty("");

    /**
     * Sets the player name displayed in the move counter label.
     *
     * @param playerName the name entered on the opening screen
     */
    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    /**
     * Called automatically by the FXML loader after all {@code @FXML} fields are injected.
     * Initialises the game state, renders the board, places the disk,
     * binds the move counter, and registers keyboard accelerators.
     */
    @FXML
    private void initialize() {
        state = new LabirynthState();
        initializeGrid();
        diskPutter();
        initializeNumberOfMoves();
        setupAccelerators();
    }

    /**
     * Clears the board {@link GridPane} and redraws every cell according to
     * the static {@link LabirynthState} board definition.
     * Goal cells are highlighted in gold; wall sides are drawn as red borders.
     */
    private void initializeGrid() {
        Logger.debug("Initializing board");
        board.getChildren().clear();
        for (var i = 0; i < LabirynthState.ROWS; i++) {
            for (var j = 0; j < LabirynthState.COLS; j++) {
                var cell = LabirynthState.getCell(i, j);
                var square = squareDesigner(cell);
                board.add(square, j, i);
            }
        }
    }

    /**
     * Builds a styled {@link StackPane} that visually represents a single labyrinth cell.
     * The background colour indicates whether the cell is the goal; each wall direction
     * that is present is rendered as a 3-pixel red border on the corresponding side.
     *
     * @param cell the {@link LabirynthCell} whose wall configuration drives the style
     * @return a styled {@code StackPane} ready to be added to the board
     */
    private StackPane squareDesigner(LabirynthCell cell) {
        var square = new StackPane();
        square.setPrefSize(44, 44);
        boolean isGoal = cell instanceof GoalLabyrinthCell;
        String bgColor = isGoal ? "#f0c040" : "#16213e";
        String topC    = cell.hasWall(Direction.NORTH) ? "#e94560" : "transparent";
        String rightC  = cell.hasWall(Direction.EAST)  ? "#e94560" : "transparent";
        String bottomC = cell.hasWall(Direction.SOUTH) ? "#e94560" : "transparent";
        String leftC   = cell.hasWall(Direction.WEST)  ? "#e94560" : "transparent";
        square.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                "-fx-border-color: " + topC + " " + rightC + " " + bottomC + " " + leftC + ";" +
                "-fx-border-width: " + (cell.hasWall(Direction.NORTH) ? 3 : 0) + " " +
                        (cell.hasWall(Direction.EAST)  ? 3 : 0) + " " +
                        (cell.hasWall(Direction.SOUTH) ? 3 : 0) + " " +
                        (cell.hasWall(Direction.WEST)  ? 3 : 0) + ";"
        );
        return square;
    }

    /**
     * Creates the red {@link Circle} that represents the disk and places it
     * in the cell that corresponds to the current disk position in {@link #state}.
     */
    private void diskPutter() {
        diskCircle = new Circle(14, Color.web("#e94560"));
        diskCircle.setStroke(Color.web("#ff8fa3"));
        diskCircle.setStrokeWidth(2);
        var cell = getCellPane(state.getPosition());
        if (cell != null) {
            cell.getChildren().add(diskCircle);
        }
    }

    /**
     * Returns the {@link StackPane} in the board that occupies the given grid position,
     * or {@code null} if no such pane exists.
     *
     * @param pos the row/column position to look up
     * @return the matching {@code StackPane}, or {@code null}
     */
    private StackPane getCellPane(Position pos) {
        for (var node : board.getChildren()) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if (row != null && col != null && row == pos.row() && col == pos.col()) {
                return (StackPane) node;
            }
        }
        return null;
    }

    /**
     * Moves the disk {@link Circle} from the {@code from} cell to the {@code to} cell
     * by reparenting it within the board's child list.
     *
     * @param from the cell the disk is leaving
     * @param to   the cell the disk is entering
     */
    private void moveDisk(Position from, Position to) {
        var fromCell = getCellPane(from);
        var toCell = getCellPane(to);
        if (fromCell != null) {
            fromCell.getChildren().remove(diskCircle);
        }
        if (toCell != null) {
            toCell.getChildren().add(diskCircle);
        }
    }

    /**
     * Handles the Up button click; moves the disk northward.
     */
    @FXML
    private void onUpButton() {
        processMove(Direction.NORTH);
    }

    /**
     * Handles the Down button click; moves the disk southward.
     */
    @FXML
    private void onDownButton() {
        processMove(Direction.SOUTH);
    }

    /**
     * Handles the Left button click; moves the disk westward.
     */
    @FXML
    private void onLeftButton() {
        processMove(Direction.WEST);
    }

    /**
     * Handles the Right button click; moves the disk eastward.
     */
    @FXML
    private void onRightButton() {
        processMove(Direction.EAST);
    }

    /**
     * Handles the Reset button click; restarts the game from the initial state.
     */
    @FXML
    private void onResetButton() {
        resetGame();
    }

    /**
     * Handles the Exit button click; terminates the JavaFX application.
     */
    @FXML
    private void onExitButton() {
        Platform.exit();
    }

    /**
     * Binds the move counter label and text field to the {@link #playerName} and
     * {@link #numberOfMoves} properties so the UI updates automatically.
     */
    private void initializeNumberOfMoves() {
        Platform.runLater(() -> Logger.debug("Játékos neve: {}", playerName.get()));
        numberOfMovesLabel.textProperty().bind(Bindings.when(playerName.isNotEmpty())
                .then(playerName.concat(" játszik, lépése.:"))
                .otherwise("Ismeretlen játékos játszik, lépése:"));
        numberOfMovesField.textProperty().bind(numberOfMoves.asString());
    }

    /**
     * Registers keyboard accelerators on the scene after it has been attached to the board.
     * Arrow keys and WASD keys trigger moves; Ctrl+R resets; Ctrl+Q exits.
     * Uses {@link Platform#runLater} because the scene is not yet available during
     * {@link #initialize()}.
     */
    private void setupAccelerators() {
        Platform.runLater(() -> {
            Logger.debug("Setting up accelerators");
            var scene = board.getScene();
            if (scene != null) {
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN), this::resetGame);
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN), Platform::exit);
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.UP),    () -> processMove(Direction.NORTH));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.RIGHT),  () -> processMove(Direction.EAST));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.DOWN),   () -> processMove(Direction.SOUTH));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.LEFT),   () -> processMove(Direction.WEST));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.W),      () -> processMove(Direction.NORTH));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.D),      () -> processMove(Direction.EAST));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.S),      () -> processMove(Direction.SOUTH));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.A),      () -> processMove(Direction.WEST));
            }
        });
    }

    /**
     * Resets the game to its initial state: creates a fresh {@link LabirynthState},
     * redraws the board, repositions the disk, and zeroes the move counter.
     */
    private void resetGame() {
        Logger.debug("Resetting game");
        state = new LabirynthState();
        initializeGrid();
        diskPutter();
        numberOfMoves.set(0);
    }

    /**
     * Validates and executes a move in the given direction.
     * If the move is illegal (wall blocks it), a warning is logged and nothing happens.
     * After a legal move, checks whether the puzzle is solved and shows the result dialog
     * if so.
     *
     * @param direction the direction in which the disk should slide
     */
    private void processMove(Direction direction) {
        Logger.info("A korong elindul ebbe az irányba: {}", direction);
        if (!state.isLegalMove(direction)) {
            Logger.warn("Fal van arra, válassz másik irányt!");
            return;
        }
        makeMoveAndUpdateUI(direction);
        if (state.isSolved()) {
            Logger.info("Megoldottad a labirintust!");
            showSolvedAndExit();
        }
    }

    /**
     * Delegates the move to {@link LabirynthState#makeMove}, updates the disk position
     * on screen, and increments the move counter.
     *
     * @param direction the direction in which the disk slides
     */
    private void makeMoveAndUpdateUI(Direction direction) {
        var oldPos = state.getPosition();
        state.makeMove(direction);
        moveDisk(oldPos, state.getPosition());
        numberOfMoves.set(numberOfMoves.get() + 1);
    }

    /**
     * Displays an information dialog congratulating the player, then exits the application.
     */
    private void showSolvedAndExit() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rejtvény megoldva!");
        alert.setHeaderText(null);
        alert.setContentText("Sikerült megoldanod a rejtvényt!");
        alert.showAndWait();
        Platform.exit();
    }
}
