package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SampleCommands {
    
    @ShellMethod(value="hello", key = "test")
    public void hello() {
        System.out.println("Hello Spring Shell!!");
    }

    @ShellMethod(value = "with parameter")
    public void hoge(int a, String b, double c) {
        System.out.println("a=" + a + ", b=" + b + ", c=" + c);
    }
}
