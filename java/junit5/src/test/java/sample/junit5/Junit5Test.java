package sample.junit5;

import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

public class Junit5Test {
    
    @Nested
    @ExtendWith(MyExtend.class)
    public class Hoge {
        
        @Test
        public void test() {
            System.out.println("Hoge.test");
        }
    }
    
    @Nested
    @ExtendWith(MyExtend.class)
    public class Fuga {
        
        @Test
        public void test() {
            System.out.println("Fuga.test");
        }
    }
}