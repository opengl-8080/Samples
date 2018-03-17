package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SampleCommands {
    
    private boolean greeted;

    @ShellMethod("Hello World")
    public void hello() {
        System.out.println("Hello!!");
        this.greeted = true;
    }
    
    public boolean isGreeted() {
        return this.greeted;
    }
}