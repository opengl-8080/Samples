package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MyControl extends HBox {

    @FXML
    private TextField text;

    public MyControl() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/my-control.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onClickButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText("text=" + this.text.getText());
        alert.showAndWait();
    }
}
