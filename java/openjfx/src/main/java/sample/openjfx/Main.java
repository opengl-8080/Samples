package sample.openjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(Main.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label label = new Label("Hello OpenJFX!!");
        label.setPrefWidth(200.0);
        label.setPrefHeight(100.0);
        Scene scene = new Scene(label);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
