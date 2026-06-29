package com.flappyguys.ui;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import com.flappyguys.game.GameController;
import com.flappyguys.game.GameState;

/**
 * Main menu screen with character and difficulty selection
 */
public class MainMenuScreen extends VBox {
    private GameController gameController;
    private ComboBox<String> characterSelector;
    private ComboBox<String> difficultySelector;

    public MainMenuScreen(GameController gameController) {
        this.gameController = gameController;
        setupUI();
    }

    private void setupUI() {
        this.setStyle("-fx-background-color: #87CEEB; -fx-padding: 20;");
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        // Title
        Label titleLabel = new Label("FLAPPY GUYS");
        titleLabel.setStyle("-fx-font-size: 48; -fx-font-weight: bold; -fx-text-fill: white;");

        // Character Selection
        Label characterLabel = new Label("Select Character:");
        characterLabel.setStyle("-fx-font-size: 18;");
        characterSelector = new ComboBox<>();
        characterSelector.getItems().addAll("Blue Guy", "Red Guy", "Yellow Guy", "Green Guy", "Purple Guy");
        characterSelector.setValue("Blue Guy");
        characterSelector.setStyle("-fx-font-size: 14; -fx-padding: 10;");

        // Difficulty Selection
        Label difficultyLabel = new Label("Select Difficulty:");
        difficultyLabel.setStyle("-fx-font-size: 18;");
        difficultySelector = new ComboBox<>();
        difficultySelector.getItems().addAll("Easy", "Medium", "Hard");
        difficultySelector.setValue("Medium");
        difficultySelector.setStyle("-fx-font-size: 14; -fx-padding: 10;");

        // Start Button
        Button startButton = new Button("START GAME");
        startButton.setStyle("-fx-font-size: 18; -fx-padding: 15 50; -fx-background-color: #90EE90; -fx-font-weight: bold;");
        startButton.setOnAction(e -> startGame());

        // Exit Button
        Button exitButton = new Button("EXIT");
        exitButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #FFB6C6;");
        exitButton.setOnAction(e -> gameController.exitGame());

        // Add components to VBox
        this.getChildren().addAll(
            titleLabel,
            new Label(" "),
            characterLabel,
            characterSelector,
            new Label(" "),
            difficultyLabel,
            difficultySelector,
            new Label(" "),
            startButton,
            exitButton
        );
    }

    private void startGame() {
        String character = characterSelector.getValue();
        String difficultyStr = difficultySelector.getValue();
        int difficulty = getDifficultyValue(difficultyStr);
        
        gameController.startGame(character, difficulty);
    }

    private int getDifficultyValue(String difficultyStr) {
        switch (difficultyStr) {
            case "Easy":
                return GameState.EASY;
            case "Medium":
                return GameState.MEDIUM;
            case "Hard":
                return GameState.HARD;
            default:
                return GameState.MEDIUM;
        }
    }
}
