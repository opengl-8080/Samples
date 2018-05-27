package sample.jigsaw.bar;

import org.apache.commons.lang3.RandomStringUtils;

public class Bar {
    
    public void hello() {
        String text = RandomStringUtils.random(10, "0123456789");
        System.out.println(text);
    }
}
