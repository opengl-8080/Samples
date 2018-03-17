package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class GreetingCommands {

    @ShellMethod("Hello World")
    public void hello() {
        System.out.println("Hello!!");
    }

    @ShellMethod("Good Bye")
    public void bye() {
        System.out.println("Bye!!");
    }
}