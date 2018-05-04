package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ImageView imageView;

    public void initStage(Stage stage) {
        stage.setWidth(500);
        stage.setHeight(400);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressBar.managedProperty().bind(progressBar.visibleProperty());
        progressBar.visibleProperty().bind(progressBar.progressProperty().lessThan(1));
        
        imageView.managedProperty().bind(imageView.visibleProperty());
        imageView.visibleProperty().bind(progressBar.progressProperty().isEqualTo(1));
        
        Image image = new Image(Paths.get("./image/shirakawago.jpg").toUri().toString(), true);
        progressBar.progressProperty().bind(image.progressProperty());
        imageView.setImage(image);
    }
}
