package sample.javafx;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

import java.io.IOException;

public class MainController {
    @FXML
    private ProgressBar progressBar;

    private Service<Void> service = new Service<Void>() {
        
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    int max = 10000000;
                    for (int i=0; i<max; i++) {
                        this.updateProgress(i, max);
                    }
                    
                    return null;
                }
            };
        }
    };
    
    @FXML
    public void start() throws IOException {
        this.service.restart();
        this.progressBar.progressProperty().bind(this.service.progressProperty());
    }
}