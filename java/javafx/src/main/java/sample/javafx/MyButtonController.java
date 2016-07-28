package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 * @see <a href="https://docs.oracle.com/javase/jp/8/javafx/api/javafx/fxml/doc-files/introduction_to_fxml.html#nested_controllers">javafx document</a>
 * @see <a href="http://d.hatena.ne.jp/hagi44/20130621/1371827337">FXML から FXML を参照する</a>
 */
public class MyButtonController {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @FXML
    public void onClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(this.message);
        alert.showAndWait();
    }
}
