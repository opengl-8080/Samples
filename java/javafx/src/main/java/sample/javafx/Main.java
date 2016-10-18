package sample.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class Main extends Application {

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
