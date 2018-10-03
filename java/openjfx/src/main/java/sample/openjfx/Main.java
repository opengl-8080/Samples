package sample.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleFinder;
import java.net.URL;
import java.nio.file.Paths;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(Main.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("jdk.module.path=" + System.getProperty("jdk.module.path"));
        URL mainFxml = Main.class.getResource("/main.fxml");
        System.out.println("mainFxml=" + mainFxml);
        Parent root = FXMLLoader.load(mainFxml);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
