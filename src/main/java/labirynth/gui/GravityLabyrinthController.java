package labirynth.gui;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import labirynth.model.Direction;
import labirynth.model.LabirynthState;
import org.pmw.tinylog.Logger;

import java.util.List;


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

    private LabirynthState state;

    @FXML
    private Label numberOfMovesLabel;


    private final IntegerProperty numberOfMoves = new SimpleIntegerProperty();

    private final StringProperty playerName =  new SimpleStringProperty();

    public void setPlayerName(String playerName)
    {
        this.playerName.set(playerName);
    }
    @FXML
    private void initialize() {
        initializeGrid();
        setupClickListeners();
        initializeNumberOfMoves();
        setupAccelerators();
    }


    private void initializeGrid() {
        Logger.debug("Initializing board");
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = new StackPane();
            }
        }
    }

    private void setupClickListeners() {
        Logger.debug("Setting up click listeners");
        for (var sideView : List.of(upButton, downButton, rightButton, leftButton)) {
            sideView.setOnMouseClicked(this::handleMouseClick);
        }
    }

    private void handleMouseClick(MouseEvent mouseEvent) {
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
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.UP), () -> processMove(Direction.NORTH));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.RIGHT), () -> processMove(Direction.EAST));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.DOWN), () -> processMove(Direction.SOUTH));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.LEFT), () -> processMove(Direction.WEST));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.W), () -> processMove(Direction.NORTH));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.D), () -> processMove(Direction.EAST));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.S), () -> processMove(Direction.SOUTH));
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.A), () -> processMove(Direction.WEST));
            }
        });

    }

    private void resetGame() {
        Logger.debug("Resetting game");
        state = new LabirynthState();
        diskPutter();
        numberOfMoves.set(0);
    }

    private void processMove(Direction direction) {
        Logger.info("A korong elindul ebbe az irányba:", direction);
        if (!state.isLegalMove(direction)) {
            Logger.warn("Fal van arra, válassz másik irányt!");
            return;
        }
        makeMoveAndUpdateUI(direction);
        if (state.isSolved()) {
            Logger.info("Megoldottad  a labirintust!");
            showSolvedAndExit();
        }
    }


}
