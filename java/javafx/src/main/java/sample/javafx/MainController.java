package sample.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {
//    @FXML
//    private BorderPane borderPane;
    @FXML
    private ImageView imageView;

    public void initStage(Stage stage) {
        stage.setWidth(300);
        stage.setHeight(200);

        System.out.println("width=" + stage.getWidth() + ", height=" + stage.getHeight());
        
        imageView.fitWidthProperty().bind(stage.widthProperty());
        imageView.fitHeightProperty().bind(stage.heightProperty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(Paths.get("./image/shirakawago.jpg").toUri().toString());
        imageView.setImage(image);
        
//        imageView.fitWidthProperty().bind(borderPane.widthProperty());
//        imageView.fitHeightProperty().bind(borderPane.heightProperty());
    }
}
