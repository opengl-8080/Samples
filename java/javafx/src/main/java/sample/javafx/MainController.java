package sample.javafx;

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
        this.label.textProperty().bind(task.textProperty());
        
        new Thread(task).start();
    }
    
    private static class MyTask extends Task<Void> {

        private StringProperty text = new SimpleStringProperty();

        public StringProperty textProperty() {
            return this.text;
        }

        @Override
        protected Void call() throws Exception {
            
            this.text.set("hello");
            
            return null;
        }
    }
}