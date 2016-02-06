package sample.junit5;

import static org.junit.gen5.api.Assertions.*;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

@DisplayName("JUnit5 のテスト")
public class Junit5Test {

    @Test
    @DisplayName("１つ目のテストやで！")
    public void test1() {
        assertEquals("hoge", "test1");
    }
    
    @Test
    public void test2() {
        assertEquals("hoge", "test2");
    }
}