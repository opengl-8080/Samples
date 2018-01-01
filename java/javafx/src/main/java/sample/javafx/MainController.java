package sample.javafx;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {
    
    private Stage stage;
    
    @FXML
    public void openFileDialog() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("C:/Program Files/java/jdk-9.0.1"));
        File file = chooser.showOpenDialog(this.stage);
        System.out.println("file=" + file);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}