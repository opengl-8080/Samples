package sample.javafx;

import javafx.concurrent.Task;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainController {

    @FXML
    public void throwException() throws IOException {
        MyTask task = new MyTask();
        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            System.out.println("exception=" + exception);
        });
        
        new Thread(task).start();
    }
    
    private static class MyTask extends Task<Void> {

        @Override
        protected Void call() throws Exception {
            throw new RuntimeException("test");
        }
    }
}