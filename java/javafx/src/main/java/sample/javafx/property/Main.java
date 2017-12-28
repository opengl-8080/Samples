package sample.javafx.property;

import javafx.util.Duration;

public class Main {
    public static void main(String[] args) {
        Duration duration = new Duration(90000.0);
        System.out.println("duration=" + duration);
        System.out.println("duration.seconds=" + duration.toSeconds());
        System.out.println("duration.minutes=" + duration.toMinutes());

        Duration oneMinute = Duration.seconds(60);
        System.out.println("oneMinute=" + oneMinute);
        System.out.println("oneMinute.minutes=" + oneMinute.toMinutes());
    }
}