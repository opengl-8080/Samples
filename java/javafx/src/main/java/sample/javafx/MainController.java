package sample.javafx;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;

import java.util.concurrent.ExecutionException;

public class MainController {
    
    @FXML
    public void startTask() throws ExecutionException, InterruptedException {
        System.out.println("Main Thread = " + Thread.currentThread().getName());
        
        Task<String> task = new Task<String>() {

            @Override
            protected String call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                return "hoge";
            }
        };

        Thread thread = new Thread(task);
        System.out.println("begin thread");
        thread.start();

        String result = task.get();
        System.out.println("end thread. result=" + result);
    }
    
    @FXML
    public void startService() {
        Service<String> service = new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {

                    @Override
                    protected String call() throws Exception {
                        System.out.println(Thread.currentThread().getName());
                        return "fuga";
                    }
                };
            }
        };

        System.out.println("start service");
        service.start();
        service.setOnSucceeded(e -> {
            System.out.println("value=" + service.getValue());
            System.out.println("thread = " + Thread.currentThread().getName());
        });
    }
}
