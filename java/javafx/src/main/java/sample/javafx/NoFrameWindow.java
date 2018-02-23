package sample.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NoFrameWindow extends Application {

    public static void main(String[] args) {
        launch(NoFrameWindow.class, args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefWidth(200);
        pane.setPrefHeight(100);
        pane.setStyle("-fx-background-radius: 50; -fx-background-color: yellow;");

        Scene scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
