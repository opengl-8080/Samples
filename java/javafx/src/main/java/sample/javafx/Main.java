package sample.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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
        Platform.setImplicitExit(false);
        
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0.0);
        primaryStage.setX(-1000);
        primaryStage.setY(-1000);
        primaryStage.show();
        
        Stage mainStage = new Stage();
        mainStage.initOwner(primaryStage);
        mainStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(this.getClass().getResource("/main.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        mainStage.setScene(scene);
        
        mainStage.show();
        
        if (SystemTray.isSupported()) {
            BufferedImage img = ImageIO.read(this.getClass().getResource("/img/open.png"));
            this.trayIcon = new TrayIcon(img);
            this.trayIcon.setImageAutoSize(true);
            this.trayIcon.addActionListener(e -> {
                Platform.runLater(() -> {
                    if (mainStage.isShowing()) {
                        mainStage.hide();
                    } else {
                        mainStage.show();
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
