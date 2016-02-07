package sample.junit5;

import static org.junit.gen5.api.Assertions.*;

import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

@ExtendWith(MyExtend.class)
public class Junit5Test {
    
    @Test
    public void test1() {
        assertEquals("hoge", "fuga");
    }
    
    @Test
    public void test2() {
        throw new NullPointerException("test");
    }
}