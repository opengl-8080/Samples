package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class GreetingCommands {

    @ShellMethod("Hello World")
    public void hello(String text) {
        System.out.println(text);
    }
}