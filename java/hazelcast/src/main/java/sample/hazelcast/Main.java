package sample.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        ClasspathXmlConfig config = new ClasspathXmlConfig("my-hazelcast.xml");
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);

        IMap<String, List<String>> map = instance.getMap("sample");
        List<String> list = new ArrayList<>();
        list.add("foo");
        list.add("bar");
        map.put("one", list);
        
        print(map);

        List<String> saved = map.get("one");
        saved.add("fizz");
        saved.add("buzz");

        System.out.println("original list=" + list);
        System.out.println("saved list=" + saved);
        print(map);
    }

    private static void print(IMap<?, ?> map) {
        String text = map.keySet().stream().sorted().map(key -> key + "=" + map.get(key)).collect(Collectors.joining(", "));
        System.out.println("map={" + text + "}");
    }
}
