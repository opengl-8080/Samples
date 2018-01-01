package sample.javafx;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class MainController {
    
    private Stage stage;
    
    @FXML
    public void openFileDialog() {
        FileChooser chooser = new FileChooser();
        List<File> files = chooser.showOpenMultipleDialog(this.stage);
        if (files == null) {
            System.out.println("files=" + files);
        } else {
            files.forEach(System.out::println);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}