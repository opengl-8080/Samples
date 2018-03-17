package sample.spring.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class SomeCommands {
    
    private final SampleCommands sampleCommands;

    public SomeCommands(SampleCommands sampleCommands) {
        this.sampleCommands = sampleCommands;
    }

    @ShellMethod("Good Bye")
    public void bye() {
        System.out.println("Bye!!");
    }

    @ShellMethod("lol")
    public void laugh() {
        System.out.println("HAHAHAHA!!");
    }

    @ShellMethodAvailability
    public Availability checkAvailability() {
        return this.sampleCommands.isGreeted()
                ? Availability.available()
                : Availability.unavailable("you does not greet yet.");
    }
}
