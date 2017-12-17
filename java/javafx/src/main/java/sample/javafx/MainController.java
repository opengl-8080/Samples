package sample.javafx;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    private Worker worker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                System.out.println("start...");
                final long max = 100000000L;
                
                for (long i=0; i<max; i++) {
                    if (this.isCancelled()) {
                        System.out.println("cancelled!!");
                        return null;
                    }
                }

                System.out.println("finished");
                return null;
            }
        };
        
        this.worker = task;
        new Thread(task).start();
    }
    
    @FXML
    public void stop() {
        this.worker.cancel();
    }
}
