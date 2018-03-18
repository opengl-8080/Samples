package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class GreetingCommands {

    @ShellMethod(value="Hello World")
    public void hello(int a, @ShellOption(defaultValue="9", help="help text") int b) {
        System.out.println("Hello!!");
    }
}