package sample.junit5;

import static org.junit.gen5.api.Assertions.*;

import org.junit.gen5.api.Test;

public class Junit5Test {

    public void test0() {
        assertEquals("hoge", "test0");
    }

    @Test
    public void test1() {
        assertEquals("hoge", "test1");
    }

    @Test
    protected void test2() {
        assertEquals("hoge", "test2");
    }

    @Test
    void test3() {
        assertEquals("hoge", "test3");
    }

    @Test
    private void test4() {
        assertEquals("hoge", "test4");
    }
}
