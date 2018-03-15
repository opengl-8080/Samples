package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class SampleCommands {
    
    @ShellMethod("Hello World")
    public void hello(int a, @ShellOption("--foo") int b, @ShellOption({"-h", "--hoge"}) int c) {
        System.out.println("a=" + a + ", b=" + b + ", c=" + c);
    }
}
