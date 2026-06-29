class Player {
    constructor(character, x, y, width, height) {
        this.character = character;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityY = 0;
        this.gravity = 0.6;
        this.jumpForce = -12;
        this.maxVelocity = 20;
    }
    
    update() {
        this.velocityY += this.gravity;
        
        if (this.velocityY > this.maxVelocity) {
            this.velocityY = this.maxVelocity;
        }
        
        this.y += this.velocityY;
    }
    
    jump() {
        this.velocityY = this.jumpForce;
    }
}