package com.flappyguys.game;

/**
 * Manages game state including score, high score, and difficulty settings
 */
public class GameState {
    private int score;
    private int highScore;
    private int difficulty;
    private boolean gameOver;
    private boolean gamePaused;
    
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;

    public GameState(int difficulty) {
        this.score = 0;
        this.highScore = loadHighScore();
        this.difficulty = difficulty;
        this.gameOver = false;
        this.gamePaused = false;
    }

    /**
     * Increase score and update high score if needed
     */
    public void addScore(int points) {
        score += points;
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }
    }

    /**
     * Get difficulty multiplier for game speed
     */
    public double getDifficultyMultiplier() {
        switch (difficulty) {
            case EASY:
                return 1.0;
            case MEDIUM:
                return 1.5;
            case HARD:
                return 2.0;
            default:
                return 1.0;
        }
    }

    /**
     * Get gap size between pipes based on difficulty
     */
    public double getGapSize() {
        switch (difficulty) {
            case EASY:
                return 150;
            case MEDIUM:
                return 120;
            case HARD:
                return 90;
            default:
                return 150;
        }
    }

    /**
     * Load high score from file
     */
    private int loadHighScore() {
        // TODO: Implement persistent storage
        return 0;
    }

    /**
     * Save high score to file
     */
    private void saveHighScore() {
        // TODO: Implement persistent storage
    }

    // Getters and setters
    public int getScore() { return score; }
    public int getHighScore() { return highScore; }
    public int getDifficulty() { return difficulty; }
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    public boolean isGamePaused() { return gamePaused; }
    public void setGamePaused(boolean gamePaused) { this.gamePaused = gamePaused; }
}
