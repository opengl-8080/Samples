package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private MediaPlayer player;
    
    @FXML
    private Label volumeLabel;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider seekSlider;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media media = new Media(Paths.get("ignore/music.m4a").toUri().toString());
        this.player = new MediaPlayer(media);
        this.player.setOnReady(() -> {
            System.out.println("duration=" + media.getDuration());
            System.out.println(player.getStopTime());
        });
        
        this.volumeLabel.textProperty().bind(this.player.volumeProperty().asString("%.2f"));
        this.player.volumeProperty().bind(this.volumeSlider.valueProperty());
        this.volumeSlider.setValue(0.05);

        this.seekSlider.valueProperty().addListener((value, oldValue, newValue) -> {
            if (seekSliderIsChanging()) {
                Duration seek = media.getDuration().multiply((Double) newValue);
                this.player.seek(seek);
            }
        });
        
        this.player.currentTimeProperty().addListener((value, oldValue, newValue) -> {
            if (!seekSliderIsChanging()) {
                double stopTime = this.player.getStopTime().toMillis();
                double currentTime = newValue.toMillis();
                this.seekSlider.setValue(currentTime / stopTime);
            }
        });
    }
    
    private boolean seekSliderIsChanging() {
        return this.seekSlider.isValueChanging() || this.seekSlider.isPressed();
    }
    
    @FXML
    public void start() {
        this.player.play();
    }
    
    @FXML
    public void stop() {
        this.player.stop();
    }
    
    @FXML
    public void pause() {
        this.player.pause();
    }
}