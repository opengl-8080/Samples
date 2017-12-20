package sample.javafx;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
        System.out.println("restart");
        this.service.restart();
    }
}