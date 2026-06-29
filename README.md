# Flappy Guys

A fun and addictive Flappy Bird-style game built with Java and JavaScript.

## Features

- 🎮 **Basic Gameplay**: Navigate through pipes and avoid obstacles
- 📊 **Score Tracking**: Keep track of your high scores
- 👥 **Multiple Characters/Skins**: Choose from different character skins
- 🔊 **Sound Effects**: Immersive audio feedback
- 🎯 **Difficulty Levels**: Easy, Medium, Hard modes
- 📱 **Mobile Support**: Play on desktop and mobile devices

## Project Structure

```
flappy-guys/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/flappyguys/
│   │           ├── game/
│   │           ├── entities/
│   │           ├── physics/
│   │           ├── audio/
│   │           └── ui/
│   └── web/
│       ├── index.html
│       ├── css/
│       ├── js/
│       └── assets/
├── assets/
│   ├── sounds/
│   ├── sprites/
│   └── characters/
└── pom.xml
```

## Getting Started

### Prerequisites
- Java 11+
- Maven

### Installation

```bash
git clone https://github.com/michaelnahrwold-web/flappy-guys.git
cd flappy-guys
mvn clean install
```

### Running the Game

```bash
mvn javafx:run
```

## How to Play

1. Click/tap the screen to make the character jump
2. Avoid the pipes and obstacles
3. Survive as long as possible to increase your score
4. Unlock new characters and difficulty levels

## License

MIT
