package sample.javafx;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainController {

    @FXML
    private Label label;

    @FXML
    public void start() throws IOException {
        MyTask task = new MyTask();
        this.label.textProperty().bind(task.valueProperty());
        
        new Thread(task).start();
    }
    
    private static class MyTask extends Task<String> {

        @Override
        protected String call() throws Exception {
            for (int i=0; i<100000000; i++) {
                this.updateValue("i=" + i);
            }
            
            return "finished!!";
        }
    }
}