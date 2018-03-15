package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class SampleCommands {

    @ShellMethod("Hello World")
    public void hello(@ShellOption(defaultValue="9") int a) {
        System.out.println("a=" + a);
    }
}