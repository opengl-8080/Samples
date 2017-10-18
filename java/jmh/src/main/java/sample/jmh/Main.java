package sample.jmh;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        for (int i=0; i<1000; i++) {
            long start = System.nanoTime();
            new Main().execute();
            long end = System.nanoTime();
            System.out.print(String.format("[%05d] %d%n", i, (end - start)));
        }
    }
    
    private void execute() {
        for (int i=0; i<10000; i++) {
            String a = "abc";
            String b = "def";
            
            String c = a + b;
        }
    }
}
