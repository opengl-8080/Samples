package sample.junit5;

import org.junit.gen5.api.AfterAll;
import org.junit.gen5.api.AfterEach;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

public class Junit5Test {
    
    @BeforeAll
    public static void beforeAll1() {
        System.out.println("beforeAll1");
    }

    @AfterAll
    public static void afterAll1() {
        System.out.println("afterAll1");
    }
    
    @BeforeEach
    public void before1() {
        System.out.println("    before1");
    }

    @AfterEach
    public void after1() {
        System.out.println("    after1");
    }
    
    public static class Base {
        
        @BeforeAll
        public static void beforeAll2() {
            System.out.println("  beforeAll2");
        }

        @AfterAll
        public static void afterAll2() {
            System.out.println("  afterAll2");
        }
        
        @BeforeEach
        public void before2() {
            System.out.println("      before2");
        }

        @AfterEach
        public void after2() {
            System.out.println("      after2");
        }
    }
    
    @Nested
    public class NestedTest extends Base {

        @BeforeEach
        public void before3() {
            System.out.println("        before3");
        }
        
        @Test
        public void test() {
            System.out.println("          test");
        }

        @AfterEach
        public void after3() {
            System.out.println("        after3");
        }
    }
}