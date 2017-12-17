package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void openOtherWindow() throws IOException {
        URL fxml = this.getClass().getResource("/other.fxml");
        FXMLLoader loader = new FXMLLoader(fxml);
        Pane pane = loader.load();

        Scene scene = new Scene(pane);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initOwner(this.stage);
        stage.initModality(Modality.WINDOW_MODAL); // ★Modality を設定

        stage.showAndWait();
    }
}