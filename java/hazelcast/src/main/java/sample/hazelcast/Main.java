package sample.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Main {
    
    public static void main(String[] args) {
        Config config = new Config();
        config.setInstanceName("ProgrammaticallyInstance");
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        System.out.println("instance.name = " + instance.getName());
    }
}
