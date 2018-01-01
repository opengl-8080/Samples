package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private CheckMenuItem checkMenuItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.checkMenuItem.selectedProperty().addListener((value, oldValue, newValue) -> {
            System.out.println(newValue ? "チェックされた" : "チェックが外れた");
        });
    }
}