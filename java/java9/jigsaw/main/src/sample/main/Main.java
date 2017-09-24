package sample.main;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Main {
    public static void main(String... args) throws Exception {
        Class<?> subClass = Class.forName("sample.sub.Sub");
        Constructor<?> constructor = subClass.getConstructor();
        Object sub = constructor.newInstance();

        Method method = subClass.getMethod("sub");
        method.invoke(sub);
    }
}