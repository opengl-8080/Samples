package sample.junit5;

import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

@ExtendWith(MyExtend.class)
public class Junit5Test {
    
    @Test
    public void test1() {
        System.out.println("test1");
    }
    
    @Test
    public void test2(String string) {
        System.out.println("test2 string=" + string);
    }
}