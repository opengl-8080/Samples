package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private MediaView mediaView;
    private MediaPlayer player;
    
    @FXML
    public void start() {
        this.player.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path music = Paths.get("./media/movie.mp4");
        Media media = new Media(music.toUri().toString());
        this.player = new MediaPlayer(media);
        this.player.setVolume(0.3);
        this.mediaView.setMediaPlayer(this.player);
    }
}