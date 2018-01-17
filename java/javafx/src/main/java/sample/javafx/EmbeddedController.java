package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmbeddedController {
    
    @FXML
    private Label label;

    public void setMessage(String message) {
        this.label.setText(message);
    }
}
