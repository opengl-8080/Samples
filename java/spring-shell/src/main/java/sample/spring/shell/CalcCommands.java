package sample.spring.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("My Commands")
public class CalcCommands {
    
    @ShellMethod("a + b")
    public int add(int a, int b) {
        return a + b;
    }
    
    @ShellMethod("a - b")
    public int minus(int a, int b) {
        return a - b;
    }
}
