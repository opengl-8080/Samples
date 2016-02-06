package sample.junit5;

import org.junit.gen5.api.Tag;
import org.junit.gen5.api.Test;

public class Junit5Test {
    
    @Test
    @Tag("hoge")
    public void test1() {
        System.out.println("[hoge] test1");
    }

    @Test
    @Tag("fuga")
    public void test2() {
        System.out.println("[fuga] test2");
    }

    @Test
    @Tag("hoge") @Tag("fuga")
    public void test3() {
        System.out.println("[hoge, fuga] test3");
    }

    @Test
    @Tag("hoge") @Tag("piyo")
    public void test4() {
        System.out.println("[hoge, piyo] test4");
    }
}