package sample.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    
    public static void main(String[] args) throws Exception {
        GroupConfig groupConfig1 = new GroupConfig();
        groupConfig1.setName("group1");
        GroupConfig groupConfig2 = new GroupConfig();
        groupConfig2.setName("group2");
        
        Config config1 = new Config();
        config1.setGroupConfig(groupConfig1);

        Config config2 = new Config();
        config2.setGroupConfig(groupConfig1);

        Config config3 = new Config();
        config3.setGroupConfig(groupConfig2);

        HazelcastInstance instance1 = Hazelcast.newHazelcastInstance(config1);
        IList<String> list = instance1.getList("list");
        list.add("foo");
        list.add("bar");

        HazelcastInstance instance2 = Hazelcast.newHazelcastInstance(config2);
        HazelcastInstance instance3 = Hazelcast.newHazelcastInstance(config3);

        Executors.newSingleThreadScheduledExecutor()
                .schedule(() -> {
                    IList<String> listAtInstance2 = instance2.getList("list");
                    System.out.println("list at instance2 = " + new ArrayList<>(listAtInstance2));

                    IList<String> listAtInstance3 = instance3.getList("list");
                    System.out.println("list at instance3 = " + new ArrayList<>(listAtInstance3));
                },10, TimeUnit.SECONDS);
    }
}
