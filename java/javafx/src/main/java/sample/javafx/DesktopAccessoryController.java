package sample.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DesktopAccessoryController {
    private Stage stage;
    private double mouseOffsetX;
    private double mouseOffsetY;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void close() {
        Platform.exit();
    }

    @FXML
    public void onMousePressed(MouseEvent event) {
        this.mouseOffsetX = this.stage.getX() - event.getScreenX();
        this.mouseOffsetY = this.stage.getY() - event.getScreenY();
    }
    
    @FXML
    public void onMouseDragged(MouseEvent event) {
        this.stage.setX(event.getScreenX() + this.mouseOffsetX);
        this.stage.setY(event.getScreenY() + this.mouseOffsetY);
    }
}