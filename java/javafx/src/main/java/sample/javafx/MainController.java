package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Pane bluePane;
    @FXML
    private Pane yellowPane;
    @FXML
    private Button button;
    @FXML
    private TextField textField;
    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.registerHandlers(this.bluePane, "bluePane");
        this.registerHandlers(this.yellowPane, "yellowPane");
//        this.registerConsumeHandlers(this.yellowPane, "yellowPane");
        this.registerHandlers(this.button, "button");
        this.registerHandlers(this.textField, "textField");
        this.registerHandlers(this.label, "label");
    }
    
    private void registerHandlers(Node node, String name) {
        node.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> System.out.println("filter " + name));
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.out.println("handler " + name));
    }

    private void registerConsumeHandlers(Node node, String name) {
        node.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            System.out.println("filter " + name);
            e.consume();
        });
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            System.out.println("handler " + name);
            e.consume();
        });
    }
}
