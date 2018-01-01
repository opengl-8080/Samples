package sample.javafx;

import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {
    
    private Stage stage;
    
    @FXML
    public void openFileDialog() {
        DirectoryChooser chooser = new DirectoryChooser();
        File directory = chooser.showDialog(this.stage);
        System.out.println("directory=" + directory);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}