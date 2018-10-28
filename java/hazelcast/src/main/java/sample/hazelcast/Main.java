package sample.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Main {
    
    public static void main(String[] args) {
        System.setProperty("instance.name", "HelloWorld");
        ClasspathXmlConfig config = new ClasspathXmlConfig("my-hazelcast.xml");
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        System.out.println(instance.getName());
    }
}
