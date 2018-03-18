package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class GreetingCommands {
    
    private boolean greeted;

    @ShellMethod("Hello World")
    public void hello() {
        System.out.println("Hello!!");
        this.greeted = true;
    }

    @ShellMethod("Good Bye")
    public void bye() {
        System.out.println("Bye!!");
    }

    public boolean isGreeted() {
        return greeted;
    }
}