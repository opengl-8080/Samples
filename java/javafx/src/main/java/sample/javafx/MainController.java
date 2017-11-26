package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class MainController {
    @FXML
    private Label dragLabel;
    @FXML
    private Label dropLabel;
    
    @FXML
    public void onDragDetected(MouseEvent e) {
        Dragboard dragboard = this.dragLabel.startDragAndDrop(TransferMode.COPY);

        ClipboardContent content = new ClipboardContent();
        content.putString("Drag Test");
        dragboard.setContent(content);

        e.consume();
    }
    
    @FXML
    public void onDragOver(DragEvent e) {
        e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        e.consume();
    }
    
    @FXML
    public void onDragDropped(DragEvent e) {
        Dragboard dragboard = e.getDragboard();
        String string = dragboard.getString();
        System.out.println("dropped string = " + string);
    }
}
