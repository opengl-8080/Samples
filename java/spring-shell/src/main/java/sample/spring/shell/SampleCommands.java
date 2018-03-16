package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class SampleCommands {

    @ShellMethod("Hello World")
    public void hello(@ShellOption(defaultValue="true") boolean a, @ShellOption(defaultValue="false") boolean b) {
        System.out.println("a=" + a + ", b=" + b);
    }
}