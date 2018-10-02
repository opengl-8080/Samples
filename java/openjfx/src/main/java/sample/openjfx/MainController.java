package sample.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Robot robot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.robot = new Robot();
    }


    @FXML
    public void clickDoButton() {
        double x = this.robot.getMouseX();
        double y = this.robot.getMouseY();
        
        this.robot.mouseMove(x - 50, y - 20);
        this.robot.mousePress(MouseButton.PRIMARY);
        this.typeWithShift(KeyCode.H);
        this.robot.keyType(KeyCode.E);
        this.robot.keyType(KeyCode.L);
        this.robot.keyType(KeyCode.L);
        this.robot.keyType(KeyCode.O);
        this.robot.keyType(KeyCode.SPACE);
        this.typeWithShift(KeyCode.W);
        this.robot.keyType(KeyCode.O);
        this.robot.keyType(KeyCode.L);
        this.robot.keyType(KeyCode.D);
        this.typeWithShift(KeyCode.EXCLAMATION_MARK);
        this.typeWithShift(KeyCode.EXCLAMATION_MARK);
    }
    private void typeWithShift(KeyCode keyCode) {
        this.robot.keyPress(KeyCode.SHIFT);
        this.robot.keyType(keyCode);
        this.robot.keyRelease(KeyCode.SHIFT);
    }
}
