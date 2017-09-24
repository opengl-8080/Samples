package sample.sub;

import sample.sub.api.Foo;
import sample.sub.internal.FooImpl;

public class FooFactory {
    public static Foo newInstance() {
        return new FooImpl();
    }
}