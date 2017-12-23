package sample.javafx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TextField textField;
    
    private DoubleProperty value = new SimpleDoubleProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Format format = new DecimalFormat("#,##0.00");
        this.textField.textProperty().bindBidirectional(this.value, format);
    }
    
    @FXML
    public void checkValue() {
        System.out.println("value=" + value.getValue());
    }
    
    @FXML
    public void resetValue() {
        this.value.set(9876.54);
    }
}