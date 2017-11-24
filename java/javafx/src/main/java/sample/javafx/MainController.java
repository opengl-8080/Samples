package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class MainController {

    @FXML
    private Label label;
    
    @FXML
    public void onDragOver(DragEvent event) {
        Dragboard dragboard = event.getDragboard();

        if (dragboard.hasFiles()) {
            dragboard.getFiles().forEach(System.out::println);
            event.acceptTransferModes(TransferMode.COPY);
        }

        event.consume();
    }
    
    @FXML
    public void onDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();

        dragboard.getFiles().forEach(System.out::println);

        event.setDropCompleted(true);
        event.consume();
    }
}
