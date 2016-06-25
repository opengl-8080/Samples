package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;

public class Main {

    public static void main(String[] args) {

        try (InputStream in = Main.class.getResourceAsStream("/config.txt")) {
            String line = new BufferedReader(new InputStreamReader(in)).readLine();
            System.out.println(line);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
