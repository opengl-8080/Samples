package sample.junit5;

import static org.junit.gen5.api.Assertions.*;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

public class Junit5Test {
    
    @Nested
    @DisplayName("◯◯の場合")
    public class NestedTest {
        
        @Test
        @DisplayName("テスト１")
        public void test1() {
            assertEquals("foo", "bar");
        }
        
        @Test
        @DisplayName("テスト２")
        public void test2() {
            assertEquals("foo", "bar");
        }
    }
}