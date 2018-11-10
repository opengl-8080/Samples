package sample.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Member;

import java.io.Console;

public class Chat {
    
    public void start() {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        String uuid = instance.getLocalEndpoint().getUuid();
        String name = uuid.substring(0, 8);

        ITopic<String> topic = instance.getTopic("messageBox");
        topic.addMessageListener(message -> {
            Member publishingMember = message.getPublishingMember();
            if (!instance.getLocalEndpoint().getUuid().equals(publishingMember.getUuid())) {
                System.out.println();
                String messageObject = message.getMessageObject();
                System.out.println(messageObject);
                System.out.print(name + " > ");
            }
        });

        Console console = System.console();
        while (true) {
            String line = console.readLine(name + " > ");
            topic.publish(name + " > " + line);
        }
    }
}
