package sample.spring.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class SampleCommands {
    
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
    
    @ShellMethod("lol")
    public void lotOfLaugh() {
        System.out.println("HAHAHAHA!!");
    }
    
    @ShellMethodAvailability({"bye", "lot-of-laugh"})
    public Availability checkAvailability() {
        return this.greeted
                ? Availability.available()
                : Availability.unavailable("you does not greet yet.");
    }
}