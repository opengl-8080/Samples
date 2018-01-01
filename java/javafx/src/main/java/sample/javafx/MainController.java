package sample.javafx;

import javafx.fxml.FXML;

public class MainController {
    
    @FXML
    public void open() {
        System.out.println("open");
    }
    
    @FXML
    public void close() {
        System.out.println("close");
    }
    
    @FXML
    public void fullScreen() {
        System.out.println("full screen");
    }
}