package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainController {
    
    @FXML
    public void openOtherWindow() throws IOException {
        URL fxml = this.getClass().getResource("/other-window.fxml");
        FXMLLoader loader = new FXMLLoader(fxml);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }
}
