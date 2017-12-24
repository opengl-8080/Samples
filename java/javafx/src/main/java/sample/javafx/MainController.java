package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    @FXML
    private Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10.0);
        
        this.pane.setEffect(gaussianBlur);
    }
}