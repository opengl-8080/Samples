package sample.junit5;

import static org.junit.gen5.api.Assertions.*;

import org.junit.gen5.api.Test;

public class Junit5Test {

    @Test
    public void test1() {
        assertEquals("hoge", "hoge");
    }

    @Test
    public void test2() {
        assertEquals("hoge", "fuga");
    }
}
