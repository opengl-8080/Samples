package sample.jline;

import jline.console.ConsoleReader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ConsoleReader console = new ConsoleReader();

        console.println("hoge");
        console.print("fuga");
        console.println("piyo");
        console.flush();

        console.println("foo");
    }
}
