package sample.junit5;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.TestInfo;

public class Junit5Test {
    
    @BeforeEach
    public void before(TestInfo info) {
        System.out.println(
            "[before]\n" +
            "displayName=" + info.getDisplayName() + "\n" +
            "name=" + info.getName()
        );
    }
    
    @Test
    @DisplayName("テスト")
    public void test(TestInfo info) {
        System.out.println(
            "[test]\n" +
            "displayName=" + info.getDisplayName() + "\n" +
            "name=" + info.getName()
        );
    }
}