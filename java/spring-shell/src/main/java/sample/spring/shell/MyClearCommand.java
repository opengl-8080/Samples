package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Clear;

@ShellComponent
public class MyClearCommand implements Clear.Command {
    
    @ShellMethod("My clear command.")
    public void clear() {
        System.out.println("my clear");
    }
}
