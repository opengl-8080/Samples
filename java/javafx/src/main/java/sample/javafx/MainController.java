package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MainController {
    
    @FXML
    public void showDialog() {
        Alert alert = new Alert(
                Alert.AlertType.INFORMATION, 
                "めっせーじ",
                ButtonType.OK,
                ButtonType.NO);
        alert.showAndWait();
    }
}
