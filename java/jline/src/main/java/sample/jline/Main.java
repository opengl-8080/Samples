package sample.jline;

import jline.console.ConsoleReader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ConsoleReader console = new ConsoleReader();

        System.out.println(console.readLine('*'));
    }
}
