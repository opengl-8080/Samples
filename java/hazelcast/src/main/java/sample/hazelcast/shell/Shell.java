package sample.hazelcast.shell;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

import java.io.Console;

public class Shell {
    private final String uuid;
    private final ITopic<String> topic;

    public Shell(HazelcastInstance instance) {
        this.uuid = instance.getLocalEndpoint().getUuid();
        this.topic = instance.getTopic("shell");
    }
    
    public void setCommandListener(CommandListener commandListener) {
        this.topic.addMessageListener(message -> {
            if (message.getPublishingMember().getUuid().equals(this.uuid)) {
                return;
            }
            
            String command = message.getMessageObject();
            commandListener.receive(command);
        });
    }
    
    public void start() {
        Console console = System.console();
        while (true) {
            String line = console.readLine();
            this.topic.publish(line);
        }
    }
}
