package sample.hazelcast.shell;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.MessageListener;

import java.io.Console;

public class Shell {
    private final String uuid;
    private final ITopic<String> topic;
    private final CommandListener commandListener;
    private final MessageProcessor messageProcessor;

    public Shell(HazelcastInstance instance, CommandListener commandListener, MessageProcessor messageProcessor) {
        this.uuid = instance.getLocalEndpoint().getUuid();
        this.topic = instance.getTopic("shell");
        this.commandListener = commandListener;
        this.messageProcessor = messageProcessor;
    }
    
    public void start() {
        this.topic.addMessageListener(message -> {
            if (message.getPublishingMember().getUuid().equals(this.uuid)) {
                return;
            }

            String command = message.getMessageObject();
            this.messageProcessor.subscribe(command);
        });
        
        Console console = System.console();
        while (true) {
            String line = console.readLine();
            this.commandListener.commandToMessage(line).ifPresent(this.topic::publish);
        }
    }
}
