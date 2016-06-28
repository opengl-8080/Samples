package sample.jline;

import jline.console.ConsoleReader;

public class Main {

    public static void main(String[] args) throws Exception {
        ConsoleReader console = new ConsoleReader();

        console.print("ABC");
        console.putString("123");
        console.flush();
        console.readLine();
    }
}
