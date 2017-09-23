package sample.sub.internal;

import sample.sub.api.Foo;

public class FooImpl implements Foo {
    @Override
    public void hello() {
        System.out.println("FooImpl.hello()");
    }
}