package sample.javafx;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.ObservableMap;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Mp3File mp3file = new Mp3File("path/to/music.m4a");

            System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
            System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
            System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
            System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
            System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
            System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));


        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
        }

        Path path = Paths.get("path/to/music.m4a");
        Media media = new Media(path.toUri().toString());

        MediaPlayer player = new MediaPlayer(media);

        player.play();

        ObservableMap<String, Object> metadata = media.getMetadata();
        System.out.println(metadata);
    }
}
