package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Game;

/**
 * Главный класс графической версии игры "Морской бой".
 */
public class BattleshipApp extends Application {

    @Override
    public void start(Stage stage) {
        Game game = new Game("Игрок 1", "Игрок 2");

        game.startGame();
        game.placeShipsAutomatically();
        game.placeShipsAutomatically();

        BorderPane root = new BorderPane();
        BoardView boardView = new BoardView(game);

        root.setCenter(boardView);

        Scene scene = new Scene(root, 900, 450);
        stage.setTitle("Морской бой");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
