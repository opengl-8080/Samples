package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class MainController {
    
    @FXML
    public void showDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("たいとる");
        alert.setHeaderText("へっだーてきすと");
        alert.setContentText("こんてんつてきすと");
        alert.showAndWait();
    }
}
