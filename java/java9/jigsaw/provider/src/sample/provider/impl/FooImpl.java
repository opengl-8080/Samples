package sample.provider.impl;

import sample.provider.api.Foo;

public class FooImpl implements Foo {
    @Override
    public void foo() {
        System.out.println("FooImpl.foo()");
    }
}