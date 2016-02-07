package sample.junit5;

import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

@ExtendWith(MyExtend.class)
public class Junit5Test {
    
    @Test
    public void test(int number) {
        System.out.println("test");
    }
}