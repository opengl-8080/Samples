package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@ShellComponent
public class SampleCommands {

    @ShellMethod("Hello World")
    public void hello(@Min(0) @Max(100) int a) {
        System.out.println("a=" + a);
    }
}