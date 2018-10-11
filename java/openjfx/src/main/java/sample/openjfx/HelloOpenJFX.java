package sample.openjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloOpenJFX extends Application {

    public static void main(String[] args) {
        Application.launch(HelloOpenJFX.class, args);
    }

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello OpenJFX!!");
        label.setStyle(
            "-fx-font-size: 50px;"
          + "-fx-font-weight: bold;"
          + "-fx-alignment: center;"
          + "-fx-text-fill: white;"
          + "-fx-background-color: linear-gradient(to bottom right, #3f95ea 0%, #52d3aa 100%);"
        );
        Scene scene = new Scene(label);
        stage.setScene(scene);
        stage.setWidth(450);
        stage.setHeight(200);
        stage.show();
    }
}