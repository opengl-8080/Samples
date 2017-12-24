package sample.javafx;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.concurrent.TimeUnit;

public class MainController {
    
    @FXML
    private Label label;
    
    @FXML
    public void start() {
        MyTask myTask = new MyTask();
        this.label.textProperty().bind(myTask.listProperty().asString());

        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }
    
    private static class MyTask extends Task<Void> {
        private ReadOnlyListWrapper<String> list = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

        public final ObservableList<String> getList() {
            return list.get();
        }

        public ReadOnlyListProperty<String> listProperty() {
            return list.getReadOnlyProperty();
        }

        @Override
        protected Void call() throws Exception {
            for (int i=0; i<10; i++) {
                String value = String.valueOf(i);
                Platform.runLater(() -> this.list.add(value));
                
                TimeUnit.SECONDS.sleep(1);
            }
            return null;
        }
    }
}