package sample.javafx;

import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private MediaView mediaView;
    private Stage stage;
    @FXML
    private BorderPane movieArea;

    public void setStage(Stage stage) {
        this.stage = stage;
        this.movieArea.prefWidthProperty().bind(this.stage.widthProperty());
        this.movieArea.prefHeightProperty().bind(this.stage.heightProperty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URI uri = Paths.get("./movies/019_2.mp4").toUri();
        Media media = new Media(uri.toString());
        media.errorProperty().addListener((InvalidationListener)(e1) -> System.out.println(e1));
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(Integer.MAX_VALUE);
        mediaPlayer.setOnReady(() -> {
            mediaPlayer.setStartTime(Duration.millis(300));
            mediaPlayer.play();
            
            this.mediaView.fitWidthProperty().bind(this.movieArea.widthProperty());
            this.mediaView.fitHeightProperty().bind(this.movieArea.heightProperty());
        });
        this.mediaView.setMediaPlayer(mediaPlayer);
    }
}
