class Game {
    constructor() {
        this.canvas = document.getElementById('gameCanvas');
        this.ctx = this.canvas.getContext('2d');
        
        // Game state
        this.gameRunning = false;
        this.gameOver = false;
        this.score = 0;
        this.highScore = localStorage.getItem('flappyGuysHighScore') || 0;
        
        // Game objects
        this.player = null;
        this.obstacles = [];
        this.difficulty = 'medium';
        this.character = 'blue';
        
        // Difficulty settings
        this.difficultySettings = {
            easy: { speed: 3, gap: 150, spawnRate: 120 },
            medium: { speed: 4.5, gap: 120, spawnRate: 100 },
            hard: { speed: 6, gap: 90, spawnRate: 80 }
        };
        
        this.spawnCounter = 0;
        this.audioManager = new AudioManager();
        
        this.setupEventListeners();
        this.updateUI();
    }
    
    setupEventListeners() {
        document.getElementById('startBtn').addEventListener('click', () => this.startGame());
        document.getElementById('restartBtn').addEventListener('click', () => this.startGame());
        document.getElementById('menuBtn').addEventListener('click', () => this.showMainMenu());
        document.getElementById('soundToggleBtn').addEventListener('click', () => this.toggleSound());
        document.getElementById('characterSelect').addEventListener('change', (e) => {
            this.character = e.target.value;
        });
        document.getElementById('difficultySelect').addEventListener('change', (e) => {
            this.difficulty = e.target.value;
        });
        
        // Input handlers
        this.canvas.addEventListener('click', () => this.handleInput());
        this.canvas.addEventListener('touchstart', () => this.handleInput());
        document.addEventListener('keydown', (e) => {
            if (e.code === 'Space') {
                this.handleInput();
                e.preventDefault();
            }
        });
    }
    
    startGame() {
        this.gameRunning = true;
        this.gameOver = false;
        this.score = 0;
        this.spawnCounter = 0;
        this.obstacles = [];
        
        this.player = new Player(this.character, 100, 250, 40, 40);
        
        document.getElementById('mainMenu').classList.remove('active');
        document.getElementById('gameOverMenu').classList.remove('active');
        document.getElementById('hud').classList.remove('hidden');
        
        this.gameLoop();
    }
    
    handleInput() {
        if (this.gameRunning && this.player) {
            this.player.jump();
            this.audioManager.playJumpSound();
        } else if (this.gameOver) {
            this.showGameOver();
        }
    }
    
    gameLoop() {
        this.update();
        this.render();
        
        if (this.gameRunning) {
            requestAnimationFrame(() => this.gameLoop());
        }
    }
    
    update() {
        if (!this.gameRunning || this.gameOver) return;
        
        const settings = this.difficultySettings[this.difficulty];
        
        // Update player
        this.player.update();
        
        // Check bounds
        if (this.player.y > this.canvas.height || this.player.y < 0) {
            this.endGame();
            return;
        }
        
        // Update and check obstacles
        for (let i = 0; i < this.obstacles.length; i++) {
            this.obstacles[i].update(settings.speed);
            
            // Collision detection
            if (this.checkCollision(this.player, this.obstacles[i])) {
                this.endGame();
                return;
            }
            
            // Scoring
            if (this.obstacles[i].x + this.obstacles[i].width < this.player.x && !this.obstacles[i].scored) {
                this.obstacles[i].scored = true;
                this.score++;
                this.audioManager.playScoreSound();
            }
        }
        
        // Remove off-screen obstacles
        this.obstacles = this.obstacles.filter(obs => obs.x + obs.width > 0);
        
        // Spawn new obstacles
        this.spawnCounter += settings.speed;
        if (this.spawnCounter >= settings.spawnRate) {
            this.obstacles.push(new Obstacle(this.canvas.width, 0, settings.gap));
            this.spawnCounter = 0;
        }
        
        this.updateUI();
    }
    
    checkCollision(player, obstacle) {
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
    
    endGame() {
        this.gameRunning = false;
        this.gameOver = true;
        this.audioManager.playCollisionSound();
        
        if (this.score > this.highScore) {
            this.highScore = this.score;
            localStorage.setItem('flappyGuysHighScore', this.highScore);
        }
        
        this.showGameOver();
    }
    
    render() {
        // Clear canvas
        this.ctx.fillStyle = '#87CEEB';
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
        
        // Draw ground
        this.ctx.fillStyle = '#8B7355';
        this.ctx.fillRect(0, this.canvas.height - 50, this.canvas.width, 50);
        
        // Draw player
        if (this.player) {
            this.drawPlayer();
        }
        
        // Draw obstacles
        this.obstacles.forEach(obs => this.drawObstacle(obs));
        
        // Draw game over screen
        if (this.gameOver) {
            this.drawGameOverScreen();
        }
    }
    
    drawPlayer() {
        const colors = {
            blue: '#0000FF',
            red: '#FF0000',
            yellow: '#FFFF00',
            green: '#00AA00',
            purple: '#800080'
        };
        
        this.ctx.fillStyle = colors[this.character] || '#0000FF';
        this.ctx.beginPath();
        this.ctx.arc(this.player.x + this.player.width / 2, this.player.y + this.player.height / 2, this.player.width / 2, 0, Math.PI * 2);
        this.ctx.fill();
        
        // Draw eye
        this.ctx.fillStyle = 'white';
        this.ctx.beginPath();
        this.ctx.arc(this.player.x + 12, this.player.y + 12, 4, 0, Math.PI * 2);
        this.ctx.fill();
        
        this.ctx.fillStyle = 'black';
        this.ctx.beginPath();
        this.ctx.arc(this.player.x + 14, this.player.y + 12, 2, 0, Math.PI * 2);
        this.ctx.fill();
    }
    
    drawObstacle(obstacle) {
        this.ctx.fillStyle = '#00AA00';
        // Top pipe
        this.ctx.fillRect(obstacle.x, 0, obstacle.width, obstacle.topHeight);
        // Bottom pipe
        this.ctx.fillRect(obstacle.x, obstacle.topHeight + obstacle.gap, obstacle.width, this.canvas.height - obstacle.topHeight - obstacle.gap - 50);
    }
    
    drawGameOverScreen() {
        // Semi-transparent overlay
        this.ctx.fillStyle = 'rgba(0, 0, 0, 0.5)';
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
        
        // Text
        this.ctx.fillStyle = 'white';
        this.ctx.font = 'bold 48px Arial';
        this.ctx.textAlign = 'center';
        this.ctx.fillText('GAME OVER', this.canvas.width / 2, this.canvas.height / 2 - 50);
        
        this.ctx.font = '28px Arial';
        this.ctx.fillText('Score: ' + this.score, this.canvas.width / 2, this.canvas.height / 2 + 30);
    }
    
    updateUI() {
        document.getElementById('scoreValue').textContent = this.score;
        document.getElementById('highScoreValue').textContent = this.highScore;
    }
    
    showGameOver() {
        document.getElementById('finalScore').textContent = this.score;
        document.getElementById('highScore').textContent = this.highScore;
        document.getElementById('gameOverMenu').classList.add('active');
        document.getElementById('hud').classList.add('hidden');
    }
    
    showMainMenu() {
        this.gameRunning = false;
        document.getElementById('mainMenu').classList.add('active');
        document.getElementById('gameOverMenu').classList.remove('active');
        document.getElementById('hud').classList.add('hidden');
    }
    
    toggleSound() {
        this.audioManager.toggleSound();
        const btn = document.getElementById('soundToggleBtn');
        if (this.audioManager.soundEnabled) {
            btn.textContent = '🔊 Sound ON';
        } else {
            btn.textContent = '🔇 Sound OFF';
        }
    }
}

// Initialize game when page loads
window.addEventListener('load', () => {
    new Game();
});