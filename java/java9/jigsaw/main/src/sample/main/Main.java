package sample.main;

import sample.sub.FooFactory;
import sample.sub.api.Foo;
import sample.sub.internal.FooImpl;

public class Main {
    public static void main(String... args) {
        FooImpl foo = (FooImpl)FooFactory.newInstance();
        foo.hello();
    }
}