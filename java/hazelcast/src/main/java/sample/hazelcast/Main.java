package sample.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        MapConfig mapConfig = new MapConfig("map");
        mapConfig.setMaxSizeConfig(new MaxSizeConfig(3, MaxSizeConfig.MaxSizePolicy.PER_NODE));
        Config config = new Config();
        config.addMapConfig(mapConfig);
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);

        IMap<Object, Object> map = instance.getMap("map");
        map.put("foo", "FOO");
        map.put("bar", "BAR");
        map.put("fizz", "FIZZ");
        print(map);

        map.put("buzz", "BUZZ");
        map.put("hoge", "HOGE");
        print(map);
    }

    private static void print(IMap<?, ?> map) {
        Map<?, ?> m = new HashMap<>(map);
        System.out.println("map=" + m);
    }
}
