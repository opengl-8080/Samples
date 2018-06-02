package sample.jigsaw.bar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BarTest {

    @Test
    void testBar() {
        Bar bar = new Bar();

        String actual = bar.method(5);

        assertEquals(actual.length(), 5);
    }
}