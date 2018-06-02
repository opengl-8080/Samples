package sample.jigsaw.test;

import org.junit.jupiter.api.Test;
import sample.jigsaw.Hoge;

import static org.junit.jupiter.api.Assertions.*;

class HogeTest {

    @Test
    void test() {
        Hoge hoge = new Hoge();

        String actual = hoge.method(10, 20);
        
        assertEquals("10 + 20 = 30", actual);
    }
}