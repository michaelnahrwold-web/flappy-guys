package com.flappyguys.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * Manages all game sound effects and music
 */
public class AudioManager {
    private MediaPlayer jumpSoundPlayer;
    private MediaPlayer collisionSoundPlayer;
    private MediaPlayer scoreSoundPlayer;
    private MediaPlayer backgroundMusicPlayer;
    
    private boolean soundEnabled = true;

    public AudioManager() {
        loadSounds();
    }

    /**
     * Load all sound files
     */
    private void loadSounds() {
        try {
            // Try to load sounds from resources - gracefully handle if they don't exist
            loadSound("jump", "jump.wav");
            loadSound("collision", "collision.wav");
            loadSound("score", "score.wav");
        } catch (Exception e) {
            System.out.println("Note: Some sound files not found. Game will work without sounds.");
        }
    }

    /**
     * Load individual sound file
     */
    private void loadSound(String soundType, String filename) {
        try {
            String resourcePath = "file:assets/sounds/" + filename;
            Media media = new Media(resourcePath);
            
            switch (soundType) {
                case "jump":
                    jumpSoundPlayer = new MediaPlayer(media);
                    jumpSoundPlayer.setVolume(0.5);
                    break;
                case "collision":
                    collisionSoundPlayer = new MediaPlayer(media);
                    collisionSoundPlayer.setVolume(0.7);
                    break;
                case "score":
                    scoreSoundPlayer = new MediaPlayer(media);
                    scoreSoundPlayer.setVolume(0.6);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Could not load sound: " + filename);
        }
    }

    /**
     * Play jump sound effect
     */
    public void playJumpSound() {
        if (soundEnabled && jumpSoundPlayer != null) {
            jumpSoundPlayer.stop();
            jumpSoundPlayer.play();
        }
    }

    /**
     * Play collision sound effect
     */
    public void playCollisionSound() {
        if (soundEnabled && collisionSoundPlayer != null) {
            collisionSoundPlayer.stop();
            collisionSoundPlayer.play();
        }
    }

    /**
     * Play score sound effect
     */
    public void playScoreSound() {
        if (soundEnabled && scoreSoundPlayer != null) {
            scoreSoundPlayer.stop();
            scoreSoundPlayer.play();
        }
    }

    /**
     * Toggle sound on/off
     */
    public void toggleSound() {
        soundEnabled = !soundEnabled;
    }

    /**
     * Check if sound is enabled
     */
    public boolean isSoundEnabled() {
        return soundEnabled;
    }
}
