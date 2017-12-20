package sample.javafx;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainController {

    private Service<Void> service = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            System.out.println("Service.createTask()");
            
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    System.out.println("Task.call()");
                    return null;
                }
            };
        }
    };
    
    @FXML
    public void start() throws IOException {
        if (!this.service.getState().equals(Worker.State.READY)) {
            System.out.println("reset");
            this.service.reset();
        }
        System.out.println("start");
        this.service.start();
    }
}