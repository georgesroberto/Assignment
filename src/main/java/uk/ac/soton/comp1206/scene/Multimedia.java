package uk.ac.soton.comp1206.scene;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Multimedia {

    private MediaPlayer audioPlayer;
    private MediaPlayer musicPlayer;

    public void playAudio(String audioFilePath) {
        Media audio = new Media(new File(audioFilePath).toURI().toString());
        if (audioPlayer != null) {
            audioPlayer.stop();
        }
        audioPlayer = new MediaPlayer(audio);
        audioPlayer.play();
    }

    public void playMusic(String musicFilePath) {
        Media music = new Media(new File(musicFilePath).toURI().toString());
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
        musicPlayer = new MediaPlayer(music);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.play();
    }

    public void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }
}
