package sample.jigsaw.bar;

import org.apache.commons.lang3.RandomStringUtils;

public class Bar {
    
    public void hello() {
        System.out.println(RandomStringUtils.random(10, "0123456789"));
    }
}
