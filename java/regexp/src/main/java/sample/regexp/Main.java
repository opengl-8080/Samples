package sample.regexp;

import org.openjdk.jmh.runner.RunnerException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws RunnerException {
        Pattern pattern = Pattern.compile("([a-z]+)([0-9]+)");
        Matcher matcher = pattern.matcher("abc123de45fg");

        int groupCount = matcher.groupCount();
        System.out.println("groupCount=" + groupCount);
        
        while (matcher.find()) {
            System.out.println("==========");
            String group = matcher.group();
            System.out.println("group=" + group);
            
            for (int i=0; i<=groupCount; i++) {
                String g = matcher.group(i);
                System.out.println("group(" + i + ")=" + g);
            }
        }
    }
}