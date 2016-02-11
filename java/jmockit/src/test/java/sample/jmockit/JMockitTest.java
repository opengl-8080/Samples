package sample.jmockit;

import org.junit.Test;

import mockit.Mocked;
import mockit.NonStrictExpectations;

public class JMockitTest {
    
    @Mocked
    private Hoge hoge;
    
    @Test
    public void test() throws Exception {
        new NonStrictExpectations() {{
            hoge.getInt(); result = new int[] {1, 2, 3};
        }};
        
        System.out.println(this.hoge.getInt());
        System.out.println(this.hoge.getInt());
        System.out.println(this.hoge.getInt());
        System.out.println(this.hoge.getInt());
    }
}
