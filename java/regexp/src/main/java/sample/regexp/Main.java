package sample.regexp;

import org.openjdk.jmh.runner.RunnerException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws RunnerException {
        test("abc");
        test("123abc456def789");
    }

    private static void test(String text) {
        Pattern pattern = Pattern.compile("[a-z]+");
        Matcher matcher = pattern.matcher(text);

        System.out.println("[text=" + text + "]");
        while (matcher.find()) {
            System.out.println("start = " + matcher.start());
            System.out.println("end = " + matcher.end());
            System.out.println("group = " + matcher.group());
        }
    }
}