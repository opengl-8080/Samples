package sample.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        ClasspathXmlConfig config = new ClasspathXmlConfig("my-hazelcast.xml");
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        MapConfig mapConfig = instance.getConfig().getMapConfig("sample");
        System.out.println(mapConfig.getEvictionPolicy());
        System.out.println(mapConfig.getMaxSizeConfig().getSize());

        IMap<Object, Object> map = instance.getMap("sample");
        map.put("one", "ONE");
        map.put("two@foo", "TWO");
        map.put("three@foo", "THREE");
        
        print(map);
    }

    private static void print(IMap<?, ?> map) {
        String text = map.keySet().stream().sorted().map(key -> key + "=" + map.get(key)).collect(Collectors.joining(", "));
        System.out.println("map={" + text + "}");
    }
}
