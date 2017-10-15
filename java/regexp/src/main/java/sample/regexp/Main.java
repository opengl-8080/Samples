package sample.regexp;

import org.openjdk.jmh.runner.RunnerException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws RunnerException {
        test("123");
        test("abc456");
        test("12ab");
    }
    
    private static void test(String text) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(text);

        System.out.println("[text=" + text + "]");
        if (matcher.lookingAt()) {
            System.out.println("lookingAt = true");
            System.out.println("start = " + matcher.start());
            System.out.println("end = " + matcher.end());
            System.out.println("group = " + matcher.group());
        } else {
            System.out.println("lookingAt = false");
        }
    }
}
