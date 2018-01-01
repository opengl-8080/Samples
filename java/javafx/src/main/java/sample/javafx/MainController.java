package sample.javafx;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;

public class MainController {
    
    private Stage stage;
    
    @FXML
    public void openFileDialog() {
        FileChooser chooser = new FileChooser();
        
        ObservableList<ExtensionFilter> extensionFilters = chooser.getExtensionFilters();
        extensionFilters.add(new ExtensionFilter("何でもあり", "*.*"));
        extensionFilters.add(new ExtensionFilter("画像だけやで", "*.jpg", "*.jpeg", "*.png", "*.gif"));
        extensionFilters.add(new ExtensionFilter("まさかの動画", "*.mp4"));
        
        File file = chooser.showOpenDialog(this.stage);
        System.out.println("file=" + file);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}