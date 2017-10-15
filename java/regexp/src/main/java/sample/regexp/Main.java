package sample.regexp;

import org.openjdk.jmh.runner.RunnerException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws RunnerException {
        Pattern pattern = Pattern.compile("[a-z]+");
        Matcher matcher = pattern.matcher("abc123def");

        System.out.println("replaceAll = " + matcher.replaceAll("*"));
        System.out.println("replaceFirst = " + matcher.replaceFirst("*"));
    }
}