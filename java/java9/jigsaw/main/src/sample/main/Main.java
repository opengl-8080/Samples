package sample.main;

import sample.sub.FooFactory;
import sample.sub.api.Foo;

public class Main {
    public static void main(String... args) {
        Foo foo = FooFactory.newInstance();
        foo.hello();
    }
}
