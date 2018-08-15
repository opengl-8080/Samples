package sample;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.ServiceLoader;
import api.MyInterface;

public class Main {
    public static void main(String... args) throws Exception {
        ServiceLoader<MyInterface> loader = ServiceLoader.load(MyInterface.class);
        Iterator<MyInterface> iterator = loader.iterator();
        while (iterator.hasNext()) {
            MyInterface mi = iterator.next();
            mi.method();

            Class<?> clazz = mi.getClass();
            Constructor constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            Method method = clazz.getMethod("method");
            method.invoke(instance);
            System.out.println(clazz.getName());
        }
    }
}