package foo;

import bar.Bar;

import java.net.URL;

public class Foo {

    public static void main(String[] args) throws Exception {
        System.out.println("jdk.module.path = " + System.getProperty("jdk.module.path"));
        
        String top_bar = "/top_bar.txt";
        String bar_bar = "/bar/bar_bar.txt";
        String top_foo = "/top_foo.txt";
        String foo_foo = "/foo/foo_foo.txt";

        new Foo().checkFindResource(Foo.class, top_bar);
        new Foo().checkFindResource(Foo.class, bar_bar);
        new Foo().checkFindResource(Foo.class, top_foo);
        new Foo().checkFindResource(Foo.class, foo_foo);
        System.out.println("--------------------");
        new Foo().checkFindResource(Bar.class, top_bar);
        new Foo().checkFindResource(Bar.class, bar_bar);
        new Foo().checkFindResource(Bar.class, top_foo);
        new Foo().checkFindResource(Bar.class, foo_foo);
        System.out.println("--------------------");
        new Bar().checkFindResource(Foo.class, top_bar);
        new Bar().checkFindResource(Foo.class, bar_bar);
        new Bar().checkFindResource(Foo.class, top_foo);
        new Bar().checkFindResource(Foo.class, foo_foo);
        System.out.println("--------------------");
        new Bar().checkFindResource(Bar.class, top_bar);
        new Bar().checkFindResource(Bar.class, bar_bar);
        new Bar().checkFindResource(Bar.class, top_foo);
        new Bar().checkFindResource(Bar.class, foo_foo);
    }
    
    public void checkFindResource(Class<?> clazz, String resource) {
        URL url = clazz.getResource(resource);
        System.out.println("@" + this.getClass().getSimpleName() + "[" + clazz.getSimpleName() + ".class.getResource(\"" + resource + "\") > " + url);
    }
}
