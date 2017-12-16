package sample.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainController implements Initializable {

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label statusLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    private ExecutorService executorService = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    });
    private Future<Void> future;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.changeButtonStatusToStopped();
        this.progressBar.setProgress(0.0);
        this.statusLabel.setText("");
    }
    
    @FXML
    public void start() {
        this.future = this.executorService.submit(() -> {
            final long max = 1000000000L;
            final long interval = max / 20L;
            
            for (long i=0; i<=max; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    Platform.runLater(() -> this.statusLabel.setText("キャンセルされました"));
                    return null;
                }
                
                if (i%interval == 0) {
                    double progress = (double)i/max;
                    Platform.runLater(() -> this.progressBar.setProgress(progress));
                }
            }
            
            Platform.runLater(() -> {
                this.statusLabel.setText("完了しました");
                this.changeButtonStatusToStopped();
            });
            
            return null;
        });
        
        this.changeButtonStatusToRunning();
        this.statusLabel.setText("実行中です...");
    }
    
    @FXML
    public void stop() {
        this.future.cancel(true);
        this.changeButtonStatusToStopped();
    }
    
    private void changeButtonStatusToRunning() {
        this.startButton.setDisable(true);
        this.stopButton.setDisable(false);
    }

    private void changeButtonStatusToStopped() {
        this.startButton.setDisable(false);
        this.stopButton.setDisable(true);
    }
}
