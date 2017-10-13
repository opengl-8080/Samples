package since9;

import since9.html.AllClassesHtml;
import since9.html.ClassHtml;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        new AllClassesHtml()
                .stream()
                .filter(ClassHtml::isSince9Type)
                .forEach(System.out::println);
    }
}
