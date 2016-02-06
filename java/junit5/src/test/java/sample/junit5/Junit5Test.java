package sample.junit5;

import org.junit.gen5.api.Test;

public class Junit5Test {
    
    @Test
    @Hoge
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }
}