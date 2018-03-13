package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SampleCommands {
    
    @ShellMethod
    public void hello() {
        System.out.println("Hello Spring Shell!!");
    }
    
    @ShellMethod(value="arguments", prefix="*")
    public void hoge(int a, int b, int c) {
        System.out.println("a=" + a + ", b=" + b + ", c=" + c);
    }
}
