package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MainController {
    
    @FXML
    public void showDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "めっせーじ", ButtonType.YES);
        Optional<ButtonType> buttonType = alert.showAndWait();
        String result = buttonType.map(ButtonType::getText).orElse("選択無し");
        System.out.println(result);
    }
}
