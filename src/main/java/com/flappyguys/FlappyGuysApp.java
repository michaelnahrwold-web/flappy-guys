package com.flappyguys;

import javafx.application.Application;
import javafx.stage.Stage;
import com.flappyguys.game.GameController;

/**
 * Main application entry point for Flappy Guys game
 */
public class FlappyGuysApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameController gameController = new GameController(primaryStage);
        gameController.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
