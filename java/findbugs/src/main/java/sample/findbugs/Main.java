package sample.findbugs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public void method() {
        String text = null;
        System.out.println("text.length=" + text.length());

        try {
            InputStream in = new FileInputStream(new File("./test"));
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        String text = null;
        System.out.println("text.length=" + text.length());

        try {
            InputStream in = new FileInputStream(new File("./test"));
        } catch (IOException e) {
        }
    }
}
