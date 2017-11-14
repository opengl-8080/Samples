package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainController {
    
    @FXML
    public void openOtherWindow() throws IOException {
        // fxml をロードして、
        URL fxml = this.getClass().getResource("/other.fxml");
        FXMLLoader loader = new FXMLLoader(fxml);
        Pane pane = loader.load();
        
        // シーンを作成
        Scene scene = new Scene(pane);
        
        // ステージにシーンを登録して
        Stage stage = new Stage();
        stage.setScene(scene);
        
        // 表示
        stage.showAndWait();
    }
}
