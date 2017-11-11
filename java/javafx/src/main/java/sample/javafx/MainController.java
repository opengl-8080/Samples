package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class MainController {
    
    @FXML
    public void showDialog() {
        new Alert(Alert.AlertType.INFORMATION).showAndWait();
        new Alert(Alert.AlertType.CONFIRMATION).showAndWait();
        new Alert(Alert.AlertType.WARNING).showAndWait();
        new Alert(Alert.AlertType.ERROR).showAndWait();
    }
}
