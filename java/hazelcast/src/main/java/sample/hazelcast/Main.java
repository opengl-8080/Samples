package sample.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.setProperty("hazelcast.logging.type", "slf4j");

        Hazelcast.newHazelcastInstance(config);
    }
}
