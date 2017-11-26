package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class MainController {
    
    @FXML
    public void onDragOver(DragEvent e) {
        e.acceptTransferModes(TransferMode.ANY);
        e.consume();
    }
    
    @FXML
    public void onDragDropped(DragEvent e) {
        Dragboard dragboard = e.getDragboard();
        String string = dragboard.getString();
        System.out.println("dropped string = " + string);
        e.consume();
    }
}
