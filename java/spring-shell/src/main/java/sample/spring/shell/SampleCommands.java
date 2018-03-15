package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;

@ShellComponent
public class SampleCommands {

    @ShellMethod("Hello World")
    public void hello(@ShellOption(arity=3) int[] a, int b) {
        System.out.println("a=" + Arrays.toString(a) + ", b=" + b);
    }
}