package sample.junit5;

import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

@ExtendWith(MyExtend.class)
public class Junit5Test {
    
    @BeforeAll
    public static void beforeAll() {
        System.out.println("beforeAll");
    }
    
    @BeforeEach
    public void before() {
        System.out.println("before");
    }
    
    @Test
    public void test1() {
        System.out.println("test1");
    }
    
    @Test
    public void test2() {
        System.out.println("test2");
    }
}