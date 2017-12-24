package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    @FXML
    private Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setWidth(20.0);
        dropShadow.setHeight(20.0);
        dropShadow.setColor(Color.valueOf("CACA00"));
        dropShadow.setSpread(0.4);

        this.pane.setEffect(dropShadow);
    }
}