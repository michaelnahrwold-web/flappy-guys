package com.flappyguys.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.animation.AnimationTimer;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import com.flappyguys.game.GameState;
import com.flappyguys.game.GameController;
import com.flappyguys.entities.Player;
import com.flappyguys.entities.Obstacle;
import com.flappyguys.physics.CollisionDetector;
import com.flappyguys.audio.AudioManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Main game screen with rendering and game loop
 */
public class GameScreen extends StackPane {
    private Canvas canvas;
    private GraphicsContext gc;
    private GameController gameController;
    private GameState gameState;
    private Player player;
    private List<Obstacle> obstacles;
    private AudioManager audioManager;
    private AnimationTimer gameLoop;

    private static final double GAME_WIDTH = 400;
    private static final double GAME_HEIGHT = 600;
    private static final double PIPE_SPEED = 4;
    private static final double SPAWN_RATE = 100;

    private double spawnCounter = 0;
    private boolean gameRunning = true;

    public GameScreen(GameController gameController, String character, int difficulty) {
        this.gameController = gameController;
        this.gameState = new GameState(difficulty);
        this.player = new Player(character);
        this.obstacles = new ArrayList<>();
        this.audioManager = new AudioManager();

        setupCanvas();
        setupEventHandlers();
        startGameLoop();
    }

    private void setupCanvas() {
        canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
    }

    private void setupEventHandlers() {
        canvas.setOnMouseClicked(this::handleMouseClick);
        canvas.setOnTouchPressed(this::handleTouchPress);
    }

    private void handleMouseClick(MouseEvent event) {
        if (gameState.isGameOver()) {
            gameController.backToMainMenu();
        } else if (!gameState.isGamePaused()) {
            player.jump();
            audioManager.playJumpSound();
        }
    }

    private void handleTouchPress(javafx.scene.input.TouchEvent event) {
        if (gameState.isGameOver()) {
            gameController.backToMainMenu();
        } else if (!gameState.isGamePaused()) {
            player.jump();
            audioManager.playJumpSound();
        }
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        };
        gameLoop.start();
    }

    private void update() {
        if (gameState.isGameOver() || gameState.isGamePaused()) {
            return;
        }

        player.update();

        // Check bounds
        if (player.isOutOfBounds(GAME_HEIGHT)) {
            gameState.setGameOver(true);
            audioManager.playCollisionSound();
            return;
        }

        // Update obstacles
        double speed = PIPE_SPEED * gameState.getDifficultyMultiplier();
        for (Obstacle obstacle : obstacles) {
            obstacle.update(speed);

            // Check collision
            if (CollisionDetector.checkCollision(player, obstacles)) {
                gameState.setGameOver(true);
                audioManager.playCollisionSound();
                return;
            }

            // Check scoring
            if (CollisionDetector.hasPassedObstacle(player, obstacle)) {
                obstacle.setScored(true);
                gameState.addScore(1);
                audioManager.playScoreSound();
            }
        }

        // Remove off-screen obstacles
        obstacles.removeIf(Obstacle::isOffScreen);

        // Spawn new obstacles
        spawnCounter += speed;
        if (spawnCounter >= SPAWN_RATE) {
            obstacles.add(new Obstacle(GAME_HEIGHT, gameState.getGapSize()));
            spawnCounter = 0;
        }
    }

    private void render() {
        // Clear canvas
        gc.setFill(Color.web("#87CEEB"));
        gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // Draw ground
        gc.setFill(Color.web("#8B7355"));
        gc.fillRect(0, GAME_HEIGHT - 50, GAME_WIDTH, 50);

        // Draw player
        drawPlayer();

        // Draw obstacles
        drawObstacles();

        // Draw HUD
        drawHUD();

        // Draw game over screen if needed
        if (gameState.isGameOver()) {
            drawGameOverScreen();
        }
    }

    private void drawPlayer() {
        Color playerColor = getPlayerColor(player.getCharacterSkin());
        gc.setFill(playerColor);
        gc.fillOval(player.getX(), player.getY(), player.getWidth(), player.getHeight());

        // Draw eye
        gc.setFill(Color.WHITE);
        gc.fillOval(player.getX() + 10, player.getY() + 10, 8, 8);
        gc.setFill(Color.BLACK);
        gc.fillOval(player.getX() + 12, player.getY() + 12, 4, 4);
    }

    private Color getPlayerColor(String skin) {
        switch (skin) {
            case "Red Guy":
                return Color.RED;
            case "Yellow Guy":
                return Color.YELLOW;
            case "Green Guy":
                return Color.GREEN;
            case "Purple Guy":
                return Color.PURPLE;
            case "Blue Guy":
            default:
                return Color.BLUE;
        }
    }

    private void drawObstacles() {
        gc.setFill(Color.GREEN);
        for (Obstacle obstacle : obstacles) {
            // Top pipe
            gc.fillRect(obstacle.getX(), 0, obstacle.getWidth(), obstacle.getTopHeight());

            // Bottom pipe
            gc.fillRect(obstacle.getX(), obstacle.getBottomY(), obstacle.getWidth(),
                    GAME_HEIGHT - obstacle.getBottomY() - 50);
        }
    }

    private void drawHUD() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(24));
        gc.setTextAlignment(TextAlignment.LEFT);
        gc.fillText("Score: " + gameState.getScore(), 20, 40);
        gc.fillText("High: " + gameState.getHighScore(), 20, 70);
    }

    private void drawGameOverScreen() {
        // Semi-transparent overlay
        gc.setFill(Color.color(0, 0, 0, 0.5));
        gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // Game Over text
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(48));
        gc.setTextAlignment(TextAlignment.CENTER);
        gc.fillText("GAME OVER", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 50);

        // Final score
        gc.setFont(new Font(28));
        gc.fillText("Score: " + gameState.getScore(), GAME_WIDTH / 2, GAME_HEIGHT / 2 + 20);

        // Instruction
        gc.setFont(new Font(18));
        gc.fillText("Click to return to menu", GAME_WIDTH / 2, GAME_HEIGHT / 2 + 80);
    }

    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}
