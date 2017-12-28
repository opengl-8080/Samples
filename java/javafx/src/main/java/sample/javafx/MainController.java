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
    public void seek() {
        this.player.seek(Duration.minutes(1));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path music = Paths.get("./media/music2.mp3");
        Media media = new Media(music.toUri().toString());
        this.player = new MediaPlayer(media);
        this.player.setStartTime(Duration.minutes(2));
        this.player.play();
    }
}