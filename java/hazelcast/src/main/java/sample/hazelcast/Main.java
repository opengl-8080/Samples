package sample.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.UrlXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    
    public static void main(String[] args) throws Exception {
        URL configFileUrl = Main.class.getResource("/my-hazelcast.xml");
        Config config = new UrlXmlConfig(configFileUrl);
        config.setConfigurationUrl(configFileUrl);

        System.out.println("instanceName=" + config.getInstanceName());
        System.out.println("working-dir-value=" + config.getProperty("working-dir-value"));
        
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);

        System.out.println("Name=" + instance.getName());
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
