package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private MyButtonController button1Controller;
    @FXML
    private MyButtonController button2Controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.button1Controller.setMessage("button1");
        this.button2Controller.setMessage("button2");
    }
}
