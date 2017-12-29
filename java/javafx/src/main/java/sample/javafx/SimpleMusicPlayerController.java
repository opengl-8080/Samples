package sample.javafx;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class SimpleMusicPlayerController implements Initializable {

    private MediaPlayer player;
    
    @FXML
    private Button playButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button pauseButton;

    @FXML
    private Label volumeLabel;
    @FXML
    private Label totalTimeLabel;
    @FXML
    private Label currentTimeLabel;
    
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider seekSlider;

    @FXML
    public void play() {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media media = new Media(Paths.get("./media/music.m4a").toUri().toString());
        this.player = new MediaPlayer(media);

        // 音量ラベル
        this.volumeLabel.textProperty().bind(this.player.volumeProperty().asString("%.2f"));
        // 合計再生時間ラベル
        this.player.setOnReady(() -> {
            Duration total = this.player.getTotalDuration();
            this.totalTimeLabel.setText(this.format(total));
        });
        // 現在の再生時間ラベル
        this.currentTimeLabel.textProperty().bind(new StringBinding() {
            {bind(player.currentTimeProperty());}
            
            @Override
            protected String computeValue() {
                Duration currentTime = player.getCurrentTime();
                return format(currentTime);
            }
        });
        
        // Playボタン
        this.playButton.disableProperty().bind(new BooleanBinding() {
            {bind(player.statusProperty());}
            
            @Override
            protected boolean computeValue() {
                boolean playable = playerStatusIsAnyOf(Status.READY, Status.PAUSED, Status.STOPPED);
                return !playable;
            }
        });
        // Stopボタン
        this.stopButton.disableProperty().bind(new BooleanBinding() {
            {bind(player.statusProperty());}
            
            @Override
            protected boolean computeValue() {
                boolean stoppable = playerStatusIsAnyOf(Status.PLAYING, Status.PAUSED, Status.STALLED);
                return !stoppable;
            }
        });
        // Pauseボタン
        this.pauseButton.disableProperty().bind(new BooleanBinding() {
            {bind(player.statusProperty());}

            @Override
            protected boolean computeValue() {
                boolean pausable = playerStatusIsAnyOf(Status.PLAYING, Status.STALLED);
                return !pausable;
            }
        });
        
        // 音量スライドバー
        this.player.volumeProperty().bind(this.volumeSlider.valueProperty());
        this.volumeSlider.setValue(1);
        
        // シークバー
        this.seekSlider.valueProperty().addListener((value, oldValue, newValue) -> {
            if (seekSliderIsChanging()) {
                Duration seekTime = this.player.getTotalDuration().multiply((Double) newValue);
                this.player.seek(seekTime);
            }
        });

        this.player.currentTimeProperty().addListener((value, oldValue, newValue) -> {
            if (!seekSliderIsChanging()) {
                double totalDuration = this.player.getTotalDuration().toMillis();
                double currentTime = newValue.toMillis();
                this.seekSlider.setValue(currentTime / totalDuration);
            }
        });

        // 再生が終了したときの後処理
        this.player.setOnEndOfMedia(() -> {
            this.player.seek(this.player.getStartTime());
            this.player.stop();
        });
    }
    
    private boolean playerStatusIsAnyOf(Status... statuses) {
        Status status = this.player.getStatus();
        return Stream.of(statuses).anyMatch(candidate -> candidate.equals(status));
    }

    private boolean seekSliderIsChanging() {
        return this.seekSlider.isValueChanging() || this.seekSlider.isPressed();
    }
    
    private String format(Duration duration) {
        long millis = (long) duration.toMillis();
        long minutes = (millis / 60_000) % 60;
        long seconds = (millis / 1_000) % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
