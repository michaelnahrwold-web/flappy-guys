package com.flappyguys.entities;

/**
 * Represents the player character with physics and animations
 */
public class Player {
    private double x;
    private double y;
    private double velocityY;
    private double width;
    private double height;
    private String characterSkin;
    
    private static final double GRAVITY = 0.6;
    private static final double JUMP_FORCE = -12;
    private static final double MAX_VELOCITY = 20;

    public Player(String characterSkin) {
        this.characterSkin = characterSkin;
        this.width = 40;
        this.height = 40;
        this.x = 100;
        this.y = 250;
        this.velocityY = 0;
    }

    /**
     * Update player physics each frame
     */
    public void update() {
        velocityY += GRAVITY;
        
        if (velocityY > MAX_VELOCITY) {
            velocityY = MAX_VELOCITY;
        }
        
        y += velocityY;
    }

    /**
     * Player jumps (flaps)
     */
    public void jump() {
        velocityY = JUMP_FORCE;
    }

    /**
     * Check if player is out of bounds
     */
    public boolean isOutOfBounds(double gameHeight) {
        return y > gameHeight || y + height < 0;
    }

    // Getters and setters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public String getCharacterSkin() { return characterSkin; }
    public double getVelocityY() { return velocityY; }

    public void setY(double y) { this.y = y; }
    public void setX(double x) { this.x = x; }
}
