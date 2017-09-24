package sample.main;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import sample.sub.Sub;

public class Main {
    public static void main(String... args) throws Exception {
        new Sub().sub();

        Class<?> clazz = Class.forName("sample.sub.foo.Foo");
        Constructor<?> constructor = clazz.getConstructor();
        Object obj = constructor.newInstance();

        Method method = clazz.getMethod("foo");
        method.invoke(obj);
    }
}