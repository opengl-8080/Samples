package sample.surrogate;

import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String text = "aA";
        byte[] bytes = text.getBytes("UTF-8");
        for (byte b : bytes) {
            System.out.println("0x" + Integer.toHexString(b));
        }
    }
}
