package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    @FXML
    private Label label;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Reflection reflection = new Reflection();
        reflection.setTopOffset(-25.0);
        reflection.setTopOpacity(0.8);
        reflection.setFraction(0.8);

        this.label.setEffect(reflection);
    }
}