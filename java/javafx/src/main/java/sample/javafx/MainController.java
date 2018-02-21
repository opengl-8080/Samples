package sample.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class MainController {
    
    @FXML
    public void close() {
        Platform.exit();
    }
}