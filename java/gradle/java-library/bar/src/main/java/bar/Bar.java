package bar;

import org.apache.commons.lang3.RandomStringUtils;

public class Bar {

    public void hello() {
        System.out.println("Bar: " + RandomStringUtils.random(10, "0123456789"));
    }
}
