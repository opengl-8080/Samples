package sample.javafx;

import javafx.concurrent.Task;
import javafx.fxml.FXML;

import java.util.concurrent.ExecutionException;

public class MainController {
    
    @FXML
    public void start() throws ExecutionException, InterruptedException {
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
}
