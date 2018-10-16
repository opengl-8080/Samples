package sample.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Config config = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        IList<String> list = instance.getList("hoge");
        System.out.println("list=" + List.copyOf(list));
        list.add("foo");
        list.add("bar");
        System.out.println("list=" + List.copyOf(list));
    }
}
