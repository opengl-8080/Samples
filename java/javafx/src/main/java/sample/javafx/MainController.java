package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MainController {
    
    @FXML
    public void showDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> buttonType = alert.showAndWait();
        
        buttonType.ifPresent(selected -> {
            System.out.println("selected=" + selected);
        });
    }
}
