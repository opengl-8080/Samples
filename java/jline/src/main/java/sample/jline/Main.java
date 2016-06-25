package sample.jline;

import jline.console.ConsoleReader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ConsoleReader console = new ConsoleReader();

        String line = console.readLine("jline > ");

        System.out.println(line);
    }
}
