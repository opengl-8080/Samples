package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class MainController {
    
    @FXML
    public void onDragOver(DragEvent e) {
        Dragboard dragboard = e.getDragboard();
        
        if (dragboard.hasHtml()) {
            e.acceptTransferModes(TransferMode.ANY);
        }

        e.consume();
    }
    
    @FXML
    public void onDragDropped(DragEvent e) {
        System.out.println("onDragDropped()");
        Dragboard dragboard = e.getDragboard();

        System.out.println("[html]   = " + dragboard.getHtml());
        System.out.println("[string] = " + dragboard.getString());
        System.out.println("[files]  = " + dragboard.getFiles());

        e.consume();
    }
}
