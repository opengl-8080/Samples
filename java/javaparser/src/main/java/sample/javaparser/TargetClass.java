package sample.javaparser;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.DoubleToIntFunction;
import java.util.stream.Stream;

/**
 * Class Javadoc comment
 */
public class TargetClass {
    /**field javadoc comment*/
    public static final String CONSTANT_VALUE = "text";
    
    static {
        System.out.println("static initializer");
    }
    
    private int i;

    public TargetClass(int i) {
        this.i = i;
    }
    
    {
        System.out.println("instance initializer");
    }

    /**
     * Method Javadoc comment.
     * @param n foo
     * @param t bar
     * @param ss s
     * @param <T> hoge
     * @param <S> s
     * @return fuga
     * @throws Exception test
     */
    @SuppressWarnings("foo")
    public <T, S extends Serializable & Closeable> String method(int n, T t, S ss) throws Exception {
        int[] array = new int[3];
        array = new int[] {1, 2, 3};
        
        for (int i=0; i<array.length; i++) {
            int element = array[i];
            System.out.println(element);
        }
        
        List<String> list = Arrays.asList("foo", "bar");
        
        for (String value : list) {
            System.out.println(value);
        }
        
        if (list.isEmpty()) {
            System.out.println("empty");
        } else if (2 < list.size()) {
            System.out.println("hoge");
        } else {
            System.out.println("fuga");
        }
        
        switch (n) {
            case 10:
                System.out.println("foo");
                break;
            default:
                System.out.println("bar");
                break;
        }

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        
        int index = 0;
        do {
            String value = list.get(index);
            System.out.println(value);
            index++;
        } while (index < list.size());

        try (Stream<String> stream = Files.lines(Paths.get("text.txt"))) {
            stream
                 .filter(String::isEmpty)
                 .map(line -> list.toString().replaceAll("[\\[\\]]", ""))
                 .forEach(System.out::println);
        } catch (IOException | UncheckedIOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
        }
        
        String s =( array.length == 0 )? "a" : "b";
        
        MyInterface<String> obj = new MyInterface<String>() {
            @Override
            public void abstractMethod() {
                System.out.println("override");
            }
        };

        {
            char c = 'a';
            int binary = 0b111;
            int hexa = 0xABC;
            int octa = 0123;
        }
        
        // line comment
        /*
         block comment
         */
        
        if (s instanceof String) {
            System.out.println("hoge");
        }

        DoubleToIntFunction a = b -> (int)b + 1;
        
        return "text";
    }
    
    enum MyEnum {
        FOO,
        BAR,
        HOGE,
        FUGA
    }
    
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface MyAnnotation {
        String value() default "text";
        int number();
    }
    
    class MyInterface2<T extends Serializable & Closeable> {
    }
    
    interface MyInterface<T> {
        void abstractMethod();
        default void defaultMethod() {
            System.out.println("default method");
        }
    }
    
    static class MyClass extends AbstractList<String> implements MyInterface {
        public MyClass() {
            this("fio");
        }
        
        public MyClass(String text) {
            super();
        }

        @Override
        public String get(int index) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void abstractMethod() {
        }
    }
}
