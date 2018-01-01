package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private ToggleGroup group1;
    @FXML
    private RadioMenuItem hogeRadioMenuItem;
    @FXML
    private RadioMenuItem fugaRadioMenuItem;
    @FXML
    private RadioMenuItem piyoRadioMenuItem;
    
    @FXML
    private ToggleGroup group2;
    @FXML
    private RadioMenuItem fizzRadioMenuItem;
    @FXML
    private RadioMenuItem buzzRadioMenuItem;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.hogeRadioMenuItem.setUserData("hoge");
        this.fugaRadioMenuItem.setUserData("fuga");
        this.piyoRadioMenuItem.setUserData("piyo");
        this.group1.selectedToggleProperty().addListener((toggle, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("[group1] " + newValue.getUserData());
            }
        });
        
        this.fizzRadioMenuItem.setUserData("fizz");
        this.buzzRadioMenuItem.setUserData("buzz");
        this.group2.selectedToggleProperty().addListener((toggle, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("[group2] " + newValue.getUserData());
            }
        });
    }
}