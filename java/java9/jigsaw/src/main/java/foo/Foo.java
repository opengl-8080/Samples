package foo;

import java.io.InputStream;
import java.net.URL;
import java.util.stream.Stream;

public class Foo {

    public static void main(String[] args) throws Exception {
        System.out.println("[jdk.module.path]");
        Stream.of(System.getProperty("jdk.module.path")
                .split(";"))
                .map(p -> "  " + p)
                .forEach(System.out::println);

        URL resource = Foo.class.getResource("/resource.txt");
        System.out.println("resource=" + resource);
    }
}
