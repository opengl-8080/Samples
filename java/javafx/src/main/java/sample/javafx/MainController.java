package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.util.List;

public class MainController {
    
    @FXML
    public void onDragOver(DragEvent e) {
        Dragboard dragboard = e.getDragboard();
        
        if (dragboard.hasFiles()) {
            e.acceptTransferModes(TransferMode.ANY);
        }

        e.consume();
    }
    
    @FXML
    public void onDragDropped(DragEvent e) {
        System.out.println("onDragDropped()");
        Dragboard dragboard = e.getDragboard();

        List<File> files = dragboard.getFiles();
        files.forEach(file -> System.out.println(file.getName()));

        e.consume();
    }
}
