package sample.jmockit;

import org.junit.Test;

import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;

public class JMockitTest {
    
    @Tested
    private Hoge hoge;
    @Injectable
    private Fuga fuga;
    @Mocked
    private Piyo piyo;
    
    @Test
    public void test() throws Exception {
        new NonStrictExpectations() {{
            fuga.toString(); result = "Mocked Fuga";
        }};
        
        this.hoge.print();
    }
}
