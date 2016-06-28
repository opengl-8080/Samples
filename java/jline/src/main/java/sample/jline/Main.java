package sample.jline;

import jline.console.ConsoleReader;

public class Main {

    public static void main(String[] args) throws Exception {
        ConsoleReader console = new ConsoleReader();

        System.out.println("1");
        console.accept();
        System.out.println("2");
    }
}
