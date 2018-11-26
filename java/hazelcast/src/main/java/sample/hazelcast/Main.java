package sample.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        ClasspathXmlConfig config = new ClasspathXmlConfig("my-hazelcast.xml");
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);

        IMap<String, Integer> map = instance.getMap("sample");
        System.out.println("size=" + map.size());
        System.out.println("foo=" + map.get("foo"));
    }

    private static void print(IMap<?, ?> map) {
        String text = map.keySet().stream().sorted().map(key -> key + "=" + map.get(key)).collect(Collectors.joining(", "));
        System.out.println("size=" + map.size() + ", map={" + text + "}");
    }
}
