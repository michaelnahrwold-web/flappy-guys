package com.flappyguys.physics;

import com.flappyguys.entities.Player;
import com.flappyguys.entities.Obstacle;
import java.util.List;

/**
 * Handles collision detection between player and obstacles
 */
public class CollisionDetector {

    /**
     * Check if player collides with any obstacle
     */
    public static boolean checkCollision(Player player, List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            if (isCollidingWithObstacle(player, obstacle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check collision between player and a single obstacle
     */
    private static boolean isCollidingWithObstacle(Player player, Obstacle obstacle) {
        double playerX = player.getX();
        double playerY = player.getY();
        double playerWidth = player.getWidth();
        double playerHeight = player.getHeight();

        double obstacleX = obstacle.getX();
        double obstacleWidth = obstacle.getWidth();
        double topPipeHeight = obstacle.getTopHeight();
        double gapStart = topPipeHeight;
        double gapEnd = topPipeHeight + obstacle.getGap();

        // Check horizontal overlap
        if (playerX + playerWidth > obstacleX && playerX < obstacleX + obstacleWidth) {
            // Check if player is within the gap
            if (playerY < gapStart || playerY + playerHeight > gapEnd) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if player passed an obstacle (for scoring)
     */
    public static boolean hasPassedObstacle(Player player, Obstacle obstacle) {
        double playerX = player.getX();
        double obstacleX = obstacle.getX();
        double obstacleWidth = obstacle.getWidth();

        // Player center passes obstacle center
        return !obstacle.isScored() && (playerX > obstacleX + obstacleWidth);
    }
}
