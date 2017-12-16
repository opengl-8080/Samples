package sample.javafx;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EmbeddedController implements Initializable {
    
    private IntegerProperty count = new SimpleIntegerProperty(0);
    
    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StringBinding output = this.count.asString("count = %d");
        this.label.textProperty().bind(output);
    }
    
    @FXML
    public void countUp() {
        int now = this.count.get();
        this.count.set(now + 1);
    }
}
