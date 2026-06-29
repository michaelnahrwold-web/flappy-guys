class Obstacle {
    constructor(gameWidth, minY, gap) {
        this.x = gameWidth;
        this.width = 60;
        this.gap = gap;
        this.topHeight = Math.random() * (600 - gap - 100) + 50;
        this.scored = false;
    }
    
    update(speed) {
        this.x -= speed;
    }
    
    isOffScreen() {
        return this.x + this.width < 0;
    }
}