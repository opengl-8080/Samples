package foo;

import bar.Bar;
import org.apache.commons.lang3.RandomStringUtils;

public class Foo {

    public static void main(String... args) {
        new Bar().hello();
        System.out.println("Foo: " + RandomStringUtils.random(10, "0123456789"));
    }
}
