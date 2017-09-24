package sample.user;

import java.util.ServiceLoader;
import sample.provider.api.Foo;

public class Main {
    public static void main(String... args) {
        for (Foo foo : ServiceLoader.load(Foo.class)) {
            foo.foo();
        }
    }
}