package sample.javafx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TextField textField;
    
    private ObjectProperty<LocalDate> value = new SimpleObjectProperty<>(LocalDate.of(2017, 1, 1));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        
        this.textField.textProperty().bindBidirectional(this.value, new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date == null) {
                    return "";
                }
                
                try {
                    return formatter.format(date);
                } catch (DateTimeException e) {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String text) {
                try {
                    return LocalDate.parse(text, formatter);
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
        });
    }
    
    @FXML
    public void checkValue() {
        System.out.println("value=" + value.getValue());
    }
    
    @FXML
    public void resetValue() {
        this.value.set(LocalDate.of(2017, 1, 1));
    }
}