package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private CheckBox checkbox;
    @FXML
    private Label hogeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hogeLabel.visibleProperty().bind(checkbox.selectedProperty());
        hogeLabel.managedProperty().bind(hogeLabel.visibleProperty());
    }
}
