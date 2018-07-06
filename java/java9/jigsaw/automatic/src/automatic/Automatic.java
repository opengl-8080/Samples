package automatic;

import java.lang.reflect.Constructor;

import bar.Bar;
import fizz.Fizz;

public class Automatic {

    public void method() throws Exception {
        System.out.println("==Automatic==");
        System.out.println("bar = " + new Bar());
        System.out.println("fizz = " + new Fizz());

        Class<?> clazz = Class.forName("foo.Foo");
        Constructor<?> constructor = clazz.getConstructor();
        Object foo = constructor.newInstance();
        System.out.println("foo = " + foo);
    }
}