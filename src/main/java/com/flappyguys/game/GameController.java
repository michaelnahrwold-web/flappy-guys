package com.flappyguys.game;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import com.flappyguys.ui.MainMenuScreen;
import com.flappyguys.ui.GameScreen;

/**
 * Controls the overall game flow and scene management
 */
public class GameController {
    private Stage primaryStage;
    private GameScreen gameScreen;
    private MainMenuScreen mainMenu;

    private static final double WINDOW_WIDTH = 400;
    private static final double WINDOW_HEIGHT = 600;

    public GameController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setupStage();
    }

    private void setupStage() {
        primaryStage.setTitle("Flappy Guys");
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.setResizable(true);
    }

    public void showMainMenu() {
        mainMenu = new MainMenuScreen(this);
        Scene scene = new Scene(mainMenu, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startGame(String character, int difficulty) {
        gameScreen = new GameScreen(this, character, difficulty);
        Scene scene = new Scene(gameScreen, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
    }

    public void backToMainMenu() {
        if (gameScreen != null) {
            gameScreen.stop();
        }
        showMainMenu();
    }

    public void exitGame() {
        primaryStage.close();
    }
}
