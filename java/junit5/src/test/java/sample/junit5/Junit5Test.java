package sample.junit5;

import org.junit.gen5.api.AfterAll;
import org.junit.gen5.api.AfterEach;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;

public class Junit5Test {

    @BeforeAll
    public static void beforeAll1() {
        System.out.println("beforeAll1");
    }

    @BeforeAll
    protected static void beforeAll2() {
        System.out.println("beforeAll2");
    }

    @BeforeAll
    static void beforeAll3() {
        System.out.println("beforeAll3");
    }

    @BeforeAll
    private static void beforeAll4() {
        System.out.println("beforeAll4");
    }

    @BeforeEach
    public void before() {
        System.out.println("  before");
    }
    
    @Test
    public void test1() {
        System.out.println("    test1");
    }
    
    @Test
    public void test2() {
        System.out.println("    test2");
    }

    @AfterEach
    public void after() {
        System.out.println("  after");
    }

    @AfterAll
    public static void afterAll1() {
        System.out.println("afterAll1");
    }

    @AfterAll
    protected static void afterAll2() {
        System.out.println("afterAll2");
    }

    @AfterAll
    static void afterAll3() {
        System.out.println("afterAll3");
    }

    @AfterAll
    private static void afterAll4() {
        System.out.println("afterAll4");
    }
}
