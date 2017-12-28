package sample.javafx;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    @FXML
    public void start() {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path music = Paths.get("./media/music2.mp3");
        Media media = new Media(music.toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        
        player.setOnReady(() -> {
            ObservableMap<String, Object> metadata = media.getMetadata();
            
            metadata.forEach((key, value) -> {
                System.out.println("key=" + key + ", value=" + value);
            });
        });
    }
}