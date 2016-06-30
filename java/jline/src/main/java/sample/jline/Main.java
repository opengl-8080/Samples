package sample.jline;

import jline.console.ConsoleReader;

public class Main {

    public static void main(String[] args) throws Exception {
        ConsoleReader console = new ConsoleReader();

        console.setBellEnabled(true);
        console.beep();
        console.flush();
    }
}
