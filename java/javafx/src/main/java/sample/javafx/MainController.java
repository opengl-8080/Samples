package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class MainController {
    
    @FXML
    public void showDialog() {
        ButtonType myButton = new ButtonType("ぼたん", ButtonBar.ButtonData.APPLY);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "めっせーじ", myButton);
        alert.showAndWait().ifPresent(System.out::println);
    }
}
