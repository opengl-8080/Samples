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
                return null;
            }
        };
        
        task.setOnScheduled(event -> System.out.println("scheduled thread=" + Thread.currentThread().getName()));
        task.setOnRunning(event -> System.out.println("running thread=" + Thread.currentThread().getName()));
        task.setOnSucceeded(event -> System.out.println("succeeded thread=" + Thread.currentThread().getName()));
        
        new Thread(task).start();
    }
}