package sample.junit5;

import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

@ExtendWith(MyExtend.class)
public class Junit5Test {
    
    @Test
    public void test(String str1, String str2) {
        System.out.println("test str1=" + str1 + ", str2=" + str2);
    }
}