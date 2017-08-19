package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private MediaView mediaView;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String source = Paths.get("movies/oow2010-2.flv").toUri().toString();
        Media media = new Media(source);
        MediaPlayer player = new MediaPlayer(media);
        this.mediaView.setMediaPlayer(player);
        player.play();

        System.out.println("play!!");
    }
}
