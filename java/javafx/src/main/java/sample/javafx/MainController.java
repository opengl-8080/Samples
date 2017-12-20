package sample.javafx;

import javafx.concurrent.Task;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainController {

    @FXML
    public void start() throws IOException {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                throw new RuntimeException("test");
            }
        };
        
        task.setOnFailed(event -> {
            Throwable exception = task.exceptionProperty().get();
            System.out.println("exception=" + exception);
        });
        
        new Thread(task).start();
    }
}