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
import java.awt.TrayIcon;
import java.awt.SystemTray;
import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DesktopAccessory extends Application {

    private TrayIcon trayIcon;
    
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        
        this.initPrimaryStage(primaryStage);
        Stage mainStage = this.initMainStage(primaryStage);
        this.initSystemTray(mainStage);
        
        primaryStage.show();
        mainStage.show();
    }
    
    private void initPrimaryStage(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0.0);
        primaryStage.setX(-1000);
        primaryStage.setY(-1000);
    }
    
    private Stage initMainStage(Stage primaryStage) throws IOException {
        Stage mainStage = new Stage();
        mainStage.initOwner(primaryStage);
        mainStage.initStyle(StageStyle.TRANSPARENT);
        
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/main.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        mainStage.setScene(scene);

        DesktopAccessoryController controller = fxmlLoader.getController();
        controller.setStage(mainStage);

        return mainStage;
    }
    
    private void initSystemTray(Stage mainStage) throws IOException, AWTException {
        if (!SystemTray.isSupported()) {
            return;
        }
        
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
        this.trayIcon.setToolTip("show/hide");
        SystemTray systemTray = SystemTray.getSystemTray();
        systemTray.add(trayIcon);
    }

    @Override
    public void stop() {
        if (SystemTray.isSupported() && this.trayIcon != null) {
            SystemTray.getSystemTray().remove(this.trayIcon);
        }
    }
}
