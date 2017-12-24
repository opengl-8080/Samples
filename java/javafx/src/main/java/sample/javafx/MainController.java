package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.MotionBlur;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    @FXML
    private Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MotionBlur motionBlur = new MotionBlur();
        motionBlur.setAngle(45.0);
        motionBlur.setRadius(40.0);
        
        this.pane.setEffect(motionBlur);
    }
}