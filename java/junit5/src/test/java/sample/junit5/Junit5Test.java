package sample.junit5;

import static org.hamcrest.CoreMatchers.*;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.junit.gen5.api.Test;

public class Junit5Test {
    
    @Test
    public void assertj() {
        Assertions.assertThat("hoge").isEqualTo("fuga");
    }
    
    @Test
    public void hamcrest() {
        MatcherAssert.assertThat("hoge", is("fuga"));
    }
}