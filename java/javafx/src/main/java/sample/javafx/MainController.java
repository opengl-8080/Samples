package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private MenuItem openMenuItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView image = new ImageView("/img/open.png");
        this.openMenuItem.setGraphic(image);
    }
}