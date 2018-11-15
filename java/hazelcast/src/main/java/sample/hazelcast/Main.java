package sample.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class Main {
    public static void main(String[] args) throws Exception {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();

        IMap<Object, Object> map = instance.getMap("map");
        System.out.println(map.getClass());
    }
}
