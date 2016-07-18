package sample.findbugs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        method();
    }

    public static void method() {
        InputStream in = null;
        try {
            in = new FileInputStream(new File("a"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
