package sample.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Main {
    
    public static void main(String[] args) {
        Config config = new Config();
        config.setProperty("hazelcast.partition.count", "231");
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        
        int size = instance.getPartitionService().getPartitions().size();
        System.out.println("partition.size=" + size);
    }
}
