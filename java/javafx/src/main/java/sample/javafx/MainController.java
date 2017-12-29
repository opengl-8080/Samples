package sample.javafx;

import javafx.fxml.Initializable;
import javafx.scene.media.AudioClip;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path music = Paths.get("./media/music2.mp3");
        AudioClip audioClip = new AudioClip(music.toUri().toString());
        audioClip.stop();
    }
}