package sample.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Help;

//@ShellComponent
public class MyHelpCommand implements Help.Command {
    
    @ShellMethod("My help command.")
    public void help() {
        System.out.println("HELP ME!!!!");
    }
}
