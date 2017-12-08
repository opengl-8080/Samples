package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Pane embeddedPane;
    
    private Stage embeddedWindow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene(this.embeddedPane);
        this.embeddedWindow = new Stage();
        this.embeddedWindow.setScene(scene);
        this.embeddedWindow.initModality(Modality.APPLICATION_MODAL);
    }
    
    @FXML
    public void openWindow() {
        this.embeddedWindow.showAndWait();
    }
}
