package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private EmbeddedController embeddedController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.embeddedController.setMessage("Hello!!");
    }
}
