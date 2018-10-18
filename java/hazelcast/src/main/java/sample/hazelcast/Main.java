package sample.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Config config = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);

        printPartitionOwners(instance);

        IList<String> list = instance.getList("hoge");
        System.out.println("list=" + List.copyOf(list));
        list.add("foo");
        list.add("bar");
        System.out.println("list=" + List.copyOf(list));

        TimeUnit.SECONDS.sleep(30);
        
        printPartitionOwners(instance);
    }
    
    private static void printPartitionOwners(HazelcastInstance instance) {
        instance
                .getPartitionService()
                .getPartitions()
                .stream()
                .collect(Collectors.groupingBy(p -> p.getOwner().getUuid()))
                .entrySet()
                .stream()
                .map(e -> "Owner.uuid=" + e.getKey() + ": Partitions.size=" + e.getValue().size())
                .forEach(System.out::println);
    }
}
