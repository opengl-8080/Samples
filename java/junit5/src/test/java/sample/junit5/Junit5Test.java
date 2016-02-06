package sample.junit5;

import org.junit.gen5.api.AfterAll;
import org.junit.gen5.api.AfterEach;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

public class Junit5Test {
    
    @BeforeAll
    public static void beforeAll() {
        System.out.println("beforeAll");
    }
    
    @BeforeEach
    public void before1() {
        System.out.println("  before1");
    }
    
    @Test
    public void test1() {
        System.out.println("    test1");
    }
    
    @AfterEach
    public void after1() {
        System.out.println("  after1");
    }
    
    @AfterAll
    public static void afterAll() {
        System.out.println("afterAll");
    }
    
    @Nested
    public class NestedClass {
        
        @BeforeEach
        public void before2() {
            System.out.println("    before2");
        }
        
        @Test
        public void test2() {
            System.out.println("      test2");
        }
        
        @AfterEach
        public void after2() {
            System.out.println("    after2");
        }
    }
}