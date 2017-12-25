package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MainController {
    
    private MediaPlayer player;
    
    @FXML
    public void start() {
        Path music = Paths.get("./media/music.m4a");
        Media media = new Media(music.toUri().toString());
        this.player = new MediaPlayer(media);
        this.player.setVolume(0.05);
        this.player.play();
    }
}