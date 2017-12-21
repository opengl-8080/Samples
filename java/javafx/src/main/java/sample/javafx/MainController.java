package sample.javafx;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.concurrent.Executors;

public class MainController {

    private Service<Void> service = new Service<Void>() {
        {
            this.setExecutor(Executors.newFixedThreadPool(3));
        }
        
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread thread = Thread.currentThread();
                    System.out.println("thread.name = " + thread.getName() + ", daemon = " + thread.isDaemon());
                    return null;
                }
            };
        }
    };
    
    @FXML
    public void start() throws IOException {
        this.service.restart();
    }
}