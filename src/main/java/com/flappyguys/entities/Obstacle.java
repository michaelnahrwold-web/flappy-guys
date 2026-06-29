package com.flappyguys.entities;

/**
 * Represents pipes and obstacles in the game
 */
public class Obstacle {
    private double x;
    private double topHeight;
    private double gap;
    private double width;
    private boolean scored;

    private static final double INITIAL_X = 800;
    private static final double WIDTH = 60;

    public Obstacle(double gameHeight, double gap) {
        this.x = INITIAL_X;
        this.width = WIDTH;
        this.gap = gap;
        this.topHeight = Math.random() * (gameHeight - gap - 100) + 50;
        this.scored = false;
    }

    /**
     * Update obstacle position each frame
     */
    public void update(double speed) {
        x -= speed;
    }

    /**
     * Check if obstacle is off screen (left side)
     */
    public boolean isOffScreen() {
        return x + width < 0;
    }

    /**
     * Get the Y position of the bottom pipe
     */
    public double getBottomY() {
        return topHeight + gap;
    }

    // Getters and setters
    public double getX() { return x; }
    public double getTopHeight() { return topHeight; }
    public double getGap() { return gap; }
    public double getWidth() { return width; }
    public double getBottomY() { return topHeight + gap; }
    public boolean isScored() { return scored; }
    public void setScored(boolean scored) { this.scored = scored; }
}
