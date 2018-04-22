package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FugaController implements Initializable {
    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.label.setText("Fuga");
    }
}
