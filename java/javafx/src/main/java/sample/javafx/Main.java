package sample.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main extends Application {

    private TrayIcon trayIcon;
    
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0.0);
        primaryStage.setX(-1000);
        primaryStage.setY(-1000);
        
        Stage main = new Stage();
        main.initOwner(primaryStage);
        main.initStyle(StageStyle.TRANSPARENT);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(300, 200);
        Button button = new Button("close");
        button.setOnAction(e -> Platform.exit());
        borderPane.setCenter(button);
        Scene scene = new Scene(borderPane);
        main.setScene(scene);

        Platform.setImplicitExit(false);
        primaryStage.show();
        main.show();
        
        if (SystemTray.isSupported()) {
            BufferedImage img = ImageIO.read(this.getClass().getResource("/img/open.png"));
            this.trayIcon = new TrayIcon(img);
            this.trayIcon.setImageAutoSize(true);
            this.trayIcon.addActionListener(e -> {
                Platform.runLater(() -> {
                    if (main.isShowing()) {
                        main.hide();
                    } else {
                        main.show();
                    }
                });
            });
            SystemTray systemTray = SystemTray.getSystemTray();
            systemTray.add(trayIcon);
        }
    }

    @Override
    public void stop() throws Exception {
        if (SystemTray.isSupported() && this.trayIcon != null) {
            SystemTray.getSystemTray().remove(this.trayIcon);
        }
    }
}
