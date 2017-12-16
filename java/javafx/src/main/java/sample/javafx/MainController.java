package sample.javafx;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label statusLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    
    private Service<Void> service = new MyService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.startButton.disableProperty().bind(this.service.runningProperty());
        this.stopButton.disableProperty().bind(this.service.runningProperty().not());
        this.statusLabel.textProperty().bind(this.service.messageProperty());
        this.progressBar.setProgress(0.0);
    }
    
    @FXML
    public void start() {
        this.service.restart();
        this.progressBar.progressProperty().bind(this.service.progressProperty());
    }
    
    @FXML
    public void stop() {
        this.service.cancel();
    }

    private static class MyService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new MyTask();
        }
    }
    
    private static class MyTask extends Task<Void> {
        
        @Override
        protected Void call() throws Exception {
            int max = 100000000;
            for (int i=0; i<=max && !this.isCancelled(); i++) {
                this.updateProgress(i, max);
            }
            return null;
        }

        @Override
        protected void running() {
            super.running();
            updateMessage("実行中です...");
        }

        @Override
        protected void cancelled() {
            super.cancelled();
            updateMessage("キャンセルされました");
        }

        @Override
        protected void succeeded() {
            super.succeeded();
            updateMessage("正常終了しました");
        }
    }
}
