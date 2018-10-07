package foo;

import java.net.URL;
import java.util.stream.Stream;

public class Foo {

    public static void main(String[] args) throws Exception {
        System.out.println("[jdk.module.path]");
        Stream.of(System.getProperty("jdk.module.path").split(";")).map(p -> "  " + p).forEach(System.out::println);

        URL fizz = Foo.class.getResource("/fizz.txt");
        System.out.println("fizz=" + fizz);

        URL buzz = Foo.class.getResource("/foo/buzz.txt");
        System.out.println("buzz=" + buzz);
    }
}
