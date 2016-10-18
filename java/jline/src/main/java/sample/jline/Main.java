package sample.jline;

import jline.console.ConsoleReader;
import jline.console.KeyMap;
import jline.console.UserInterruptException;

public class Main {

    public static void main(String[] args) throws Exception {
        ConsoleReader console = new ConsoleReader();
        console.setPrompt("jline>");
        console.setHandleUserInterrupt(true);
        console.setKeyMap(KeyMap.VI_MOVE);

        try {
            while (true) {
                console.readLine();
            }
        } catch (UserInterruptException e) {
            // ignore
        }
    }
}