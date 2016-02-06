package sample.junit5;

import org.junit.gen5.api.AfterEach;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;

public class Junit5Test {

    @BeforeEach
    public void before1() {
        System.out.println("before1");
    }

    @BeforeEach
    protected void before2() {
        System.out.println("before2");
    }

    @BeforeEach
    void before3() {
        System.out.println("before3");
    }

    @BeforeEach
    private void before4() {
        System.out.println("before4");
    }
    
    @Test
    public void test1() {
        System.out.println("  test1");
    }
    
    @Test
    public void test2() {
        System.out.println("  test2");
    }

    @AfterEach
    public void after1() {
        System.out.println("after1");
    }

    @AfterEach
    protected void after2() {
        System.out.println("after2");
    }

    @AfterEach
    void after3() {
        System.out.println("after3");
    }

    @AfterEach
    private void after4() {
        System.out.println("after4");
    }
}