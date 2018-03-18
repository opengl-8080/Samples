package sample.spring.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;

//@Component
public class MyPromptProvider implements PromptProvider {
    
    private final GreetingCommands greetingCommands;

    public MyPromptProvider(GreetingCommands greetingCommands) {
        this.greetingCommands = greetingCommands;
    }

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("greeted > ", AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE));
//        return this.greetingCommands.isGreeted()
//                ? new AttributedString("greeted > ", AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
//                : new AttributedString("not greeted > ", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
    }
}
