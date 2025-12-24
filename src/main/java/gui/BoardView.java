package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.Board;
import models.Game;
import models.Player;

/**
 * Графическое представление игрового поля.
 */
public class BoardView extends VBox {

    private final Game game;
    private final GridPane opponentGrid;
    private final Label statusLabel;

    public BoardView(Game game) {
        this.game = game;
        this.opponentGrid = new GridPane();
        this.statusLabel = new Label();

        setSpacing(10);
        setAlignment(Pos.CENTER);

        drawOpponentBoard();
        updateStatus();

        getChildren().addAll(statusLabel, opponentGrid);
    }

    private void drawOpponentBoard() {
        opponentGrid.getChildren().clear();
        Player opponent = game.getOpponent();

        for (int x = 0; x < Board.SIZE; x++) {
            for (int y = 0; y < Board.SIZE; y++) {
                Button cell = new Button();
                cell.setPrefSize(40, 40);

                int row = x;
                int col = y;

                cell.setOnAction(e -> handleShot(cell, row, col));

                opponentGrid.add(cell, col, row);
            }
        }
    }

    private void handleShot(Button cell, int x, int y) {
        Player opponent = game.getOpponent();
        Board.ShotResult result = opponent.getBoard().receiveShot(x, y);

        switch (result) {
            case HIT -> {
                cell.setStyle("-fx-background-color: red;");
                updateStatus();
            }
            case SUNK -> {
                cell.setStyle("-fx-background-color: darkred;");
                if (game.checkWinCondition()) {
                    statusLabel.setText("🏆 Победил " + game.getCurrentPlayer().getName());
                    opponentGrid.setDisable(true);
                }
            }
            case MISS -> {
                cell.setStyle("-fx-background-color: lightblue;");
                game.switchPlayer();
                updateStatus();
            }
            default -> {}
        }

        cell.setDisable(true);
    }

    private void updateStatus() {
        statusLabel.setText("Ход игрока: " + game.getCurrentPlayer().getName());
    }
}
