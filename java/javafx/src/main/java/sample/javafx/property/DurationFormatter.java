package sample.javafx.property;

import javafx.util.Duration;

public class DurationFormatter {
    
    public static String format(Duration duration) {
        long millis = (long) duration.toMillis();
        long absMillis = Math.abs(millis);
        long hours = absMillis / 3_600_000;
        long minutes = (absMillis / 60_000) % 60;
        long seconds = (absMillis / 1_000) % 60;
        long milliseconds = absMillis % 1_000;
        
        return (millis < 0 ? "-" : "") + String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }
}
