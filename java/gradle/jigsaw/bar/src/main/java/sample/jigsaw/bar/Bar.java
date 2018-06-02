package sample.jigsaw.bar;

import org.apache.commons.lang3.RandomStringUtils;

public class Bar {
    
    public String method(int size) {
        return RandomStringUtils.random(size, "0123456789");
    }
}
