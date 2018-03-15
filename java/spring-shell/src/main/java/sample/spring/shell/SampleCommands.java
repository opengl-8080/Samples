package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SampleCommands {
    
    @ShellMethod(value="Hello World", prefix="-")
    public void hello(int a) {
        System.out.println("a=" + a);
    }
}
