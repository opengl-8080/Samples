package sample.javafx;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private ProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                final long max = 100000000L;
                
                for (long i=0; i<=max; i++) {
                    this.updateProgress(i, max);
                }
                
                return null;
            }
        };
        
        this.progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }
}
