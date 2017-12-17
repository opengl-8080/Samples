package sample.javafx;

import javafx.concurrent.Task;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Runnable task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                System.out.println("task.call()");
                System.out.println("thread = " + Thread.currentThread().getName());
                return null;
            }
        };
        
        new Thread(task).start();
    }
}
