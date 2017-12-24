package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    @FXML
    private Pane pane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetX(7.0);
        innerShadow.setOffsetY(7.0);
        innerShadow.setColor(Color.valueOf("500"));
        innerShadow.setRadius(10.0);
        
        this.pane.setEffect(innerShadow);
    }
}