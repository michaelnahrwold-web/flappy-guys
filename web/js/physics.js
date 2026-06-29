class Physics {
    static checkCollision(player, obstacle) {
        const playerX = player.x;
        const playerY = player.y;
        const playerW = player.width;
        const playerH = player.height;
        
        const obsX = obstacle.x;
        const obsW = obstacle.width;
        const gapStart = obstacle.topHeight;
        const gapEnd = obstacle.topHeight + obstacle.gap;
        
        // Check horizontal overlap
        if (playerX + playerW > obsX && playerX < obsX + obsW) {
            // Check if player is within gap
            if (playerY < gapStart || playerY + playerH > gapEnd) {
                return true;
            }
        }
        return false;
    }
}