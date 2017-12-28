package sample.javafx.property;

import javafx.util.Duration;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class DurationFormatterTest {
    
    @Test
    public void test1() throws Exception {
        positive(0, 0, 0, 1, "00:00:00.001");
    }

    @Test
    public void test2() throws Exception {
        positive(0, 0, 1, 0, "00:00:01.000");
    }

    @Test
    public void test3() throws Exception {
        positive(3, 14, 59, 23, "03:14:59.023");
    }

    @Test
    public void test4() throws Exception {
        positive(28, 1, 34, 0, "28:01:34.000");
    }

    @Test
    public void test5() throws Exception {
        negative(28, 1, 34, 0, "-28:01:34.000");
    }

    private void negative(int hours, int minutes, int seconds, int millis, String expected) {
        double milliseconds = millis
                + (1000.0 * seconds)
                + (1000.0 * 60 * minutes)
                + (1000.0 * 60 * 60 * hours);

        Duration duration = Duration.millis(-1 * milliseconds);

        String actual = DurationFormatter.format(duration);

        assertThat(actual).isEqualTo(expected);
    }
    
    private void positive(int hours, int minutes, int seconds, int millis, String expected) {
        double milliseconds = millis
                            + (1000.0 * seconds)
                            + (1000.0 * 60 * minutes)
                            + (1000.0 * 60 * 60 * hours);

        Duration duration = Duration.millis(milliseconds);

        String actual = DurationFormatter.format(duration);
        
        assertThat(actual).isEqualTo(expected);
    }
}