package sample.jline;

import jline.console.ConsoleReader;

public class Main {

    public static void main(String[] args) throws Exception {
        ConsoleReader console = new ConsoleReader();
        console.putString("12345");
        console.flush();

        console.moveCursor(-3);
        console.killLine();
        console.flush();
    }
}
