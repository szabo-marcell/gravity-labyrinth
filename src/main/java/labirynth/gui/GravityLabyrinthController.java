package labirynth.gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import labirynth.model.LabirynthState;
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
    private LabirynthState state;
    @FXML
    private Label numberOfMovesLabel;

    private final IntegerProperty numberOfMoves = new SimpleIntegerProperty();

    private final StringProperty playerName =  new SimpleStringProperty();

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }
    @FXML
    private void initialize() {
        initializeGrid();
    }

    private void initializeGrid() {
        Logger.debug("Initializing board");
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = new StackPane();
            }
        }
    }



}
