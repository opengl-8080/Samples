package sample.javafx;

import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL url = MainController.class.getResource("/media/music2.mp3");
        Media media = new Media(url.toString());
        MediaPlayer player = new MediaPlayer(media);
        
        player.play();
    }
}