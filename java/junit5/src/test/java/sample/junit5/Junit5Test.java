package sample.junit5;

import static org.junit.gen5.api.Assumptions.*;

import org.junit.gen5.api.Test;

public class Junit5Test {
    
    @Test
    public void test1() {
        assumeTrue(true);
        System.out.println("test1");
    }
    
    @Test
    public void test2() {
        assumeTrue(false);
        System.out.println("test2");
    }
}