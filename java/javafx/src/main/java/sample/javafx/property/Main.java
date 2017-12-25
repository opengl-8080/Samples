package sample.javafx.property;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path music = Paths.get("./media/music.m4a");
        Media media = new Media(music.toUri().toString());
        MediaPlayer player = new MediaPlayer(media);

        player.play();
    }
}