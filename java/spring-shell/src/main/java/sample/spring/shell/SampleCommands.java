package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SampleCommands {
    
    @ShellMethod("Hello World")
    public void hello(int a, int b, int fooBar) {
        System.out.println("a=" + a + ", b=" + b + ", fooBar=" + fooBar);
    }
}
