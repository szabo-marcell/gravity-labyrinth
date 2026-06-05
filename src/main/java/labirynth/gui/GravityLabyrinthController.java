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
    private TextField numberOfMovesField;

    @FXML
    private Label numberOfMovesLabel;

    private LabirynthState state;

    private Circle diskCircle;

    private final IntegerProperty numberOfMoves = new SimpleIntegerProperty();

    private final StringProperty playerName = new SimpleStringProperty("");

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    @FXML
    private void initialize() {
        state = new LabirynthState();
        initializeGrid();
        diskPutter();
        initializeNumberOfMoves();
        setupAccelerators();
        resetGame();
    }

    private void initializeGrid() {
        Logger.debug("Initializing board");
        board.getChildren().clear();
        for (var i = 0; i < LabirynthState.ROWS; i++) {
            for (var j = 0; j < LabirynthState.COLS; j++) {
                var cell = LabirynthState.getCell(i, j);
                var square= squareDesigner(cell);
                board.add(square, j, i);
            }
        }
    }

    private StackPane squareDesigner(LabirynthCell cell)
    {
        var square = new StackPane();
        square.setPrefSize(44, 44);
        boolean isGoal = cell instanceof GoalLabyrinthCell;
        String bgColor = isGoal ? "#f0c040" : "#16213e";
        String topC    = cell.hasWall(Direction.NORTH)   ? "#e94560" : "transparent";
        String rightC  = cell.hasWall(Direction.EAST) ? "#e94560" : "transparent";
        String bottomC = cell.hasWall(Direction.SOUTH)? "#e94560" : "transparent";
        String leftC   = cell.hasWall(Direction.WEST) ? "#e94560" : "transparent";
        square.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-border-color: " + topC + " " + rightC + " " + bottomC + " " + leftC + ";" +
                        "-fx-border-width: " + (cell.hasWall(Direction.NORTH) ?  3 : 0) + " " +
                        (cell.hasWall(Direction.EAST) ?  3 : 0)+ " " + (cell.hasWall(Direction.SOUTH) ?  3 : 0)+ " " +
                        (cell.hasWall(Direction.WEST) ?  3 : 0)+ ";"
        );
        return square;
    }

    private void diskPutter() {
        diskCircle = new Circle(14, Color.web("#e94560"));
        diskCircle.setStroke(Color.web("#ff8fa3"));
        diskCircle.setStrokeWidth(2);
        var cell = getCellPane(state.getPosition());
        if (cell != null) {
            cell.getChildren().add(diskCircle);
        }
    }

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

    @FXML
    private void onUpButton() {
        processMove(Direction.NORTH);
    }

    @FXML
    private void onDownButton() {
        processMove(Direction.SOUTH);
    }

    @FXML
    private void onLeftButton() {
        processMove(Direction.WEST);
    }

    @FXML
    private void onRightButton() {
        processMove(Direction.EAST);
    }

    private void initializeNumberOfMoves() {
        Platform.runLater(() -> Logger.debug("Játékos neve: {}", playerName.get()));
        numberOfMovesLabel.textProperty().bind(Bindings.when(playerName.isNotEmpty())
                .then(playerName.concat(" játszik, lépése.:"))
                .otherwise("Ismeretlen játékos játszik, lépése:"));
        numberOfMovesField.textProperty().bind(numberOfMoves.asString());
    }

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

    private void resetGame() {
        Logger.debug("Resetting game");
        state = new LabirynthState();
        initializeGrid();
        diskPutter();
        numberOfMoves.set(0);
    }

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

    private void makeMoveAndUpdateUI(Direction direction) {
        var oldPos = state.getPosition();
        state.makeMove(direction);
        moveDisk(oldPos, state.getPosition());
        numberOfMoves.set(numberOfMoves.get() + 1);
    }

    private void showSolvedAndExit() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rejtvény megoldva!");
        alert.setHeaderText(null);
        alert.setContentText("Sikerült megoldanod a rejtvényt!");
        alert.showAndWait();
        Platform.exit();
    }
}
