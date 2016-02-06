package sample.junit5;

import static org.junit.gen5.api.Assertions.*;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

@RunWith(JUnit5.class)
public class Junit5Test {
    
    @Test
    @DisplayName("テストです")
    public void test() {
        assertEquals("hoge", "fuga");
    }
}