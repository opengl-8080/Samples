package sample.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Properties;

public class Main {
    
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("instance.name", "Hoge");
        ClasspathXmlConfig config = new ClasspathXmlConfig("my-hazelcast.xml", properties);

        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        System.out.println(instance.getName());
    }
}
