package sample.regexp;

import org.openjdk.jmh.runner.RunnerException;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws RunnerException {
        test("[default]", () -> Pattern.compile("^[a-z]+$"));
        test("[MULTILINE]", () -> Pattern.compile("^[a-z]+$", Pattern.MULTILINE));
    }
    
    private static void test(String label, Supplier<Pattern> patternSupplier) {
        System.out.println(label);
        Pattern pattern = patternSupplier.get();

        String text = "abc\n"
                    + "def\n";

        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);
        }
    }
}
