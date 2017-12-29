package sample.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = this.getClass().getResource("/simple-music-player.fxml");
        FXMLLoader loader = new FXMLLoader(url);

        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("my-style.css");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
