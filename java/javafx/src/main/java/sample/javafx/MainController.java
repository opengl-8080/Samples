package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private MediaPlayer player;
    
    @FXML
    public void start() {
        this.player.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path music = Paths.get("./media/music.mp3");
        Media media = new Media(music.toUri().toString());
        this.player = new MediaPlayer(media);
        
        this.player.setCycleCount(3);

        Duration cycleDuration = this.player.getCycleDuration();
        System.out.println("cycleDuration=" + cycleDuration);
        Duration totalDuration = this.player.getTotalDuration();
        System.out.println("totalDuration=" + totalDuration);

        this.player.setOnReady(() -> {
            System.out.println("[onReady] cycleDuration=" + this.player.getCycleDuration());
            System.out.println("[onReady] totalDuration=" + this.player.getTotalDuration());
        });
    }
}