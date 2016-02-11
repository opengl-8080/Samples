package sample.jmockit;

import org.junit.Test;

import mockit.Tested;

public class JMockitTest {
    
    @Tested
    private Hoge hoge;
    
    @Test
    public void test() throws Exception {
        System.out.println(this.hoge);
    }
}
