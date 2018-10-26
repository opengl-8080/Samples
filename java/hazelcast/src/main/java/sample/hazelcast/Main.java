package sample.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.matcher.RegexConfigPatternMatcher;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class Main {
    
    public static void main(String[] args) {
        Config config = new Config();
        config.setConfigPatternMatcher(new RegexConfigPatternMatcher());

        MapConfig allMapConfig = new MapConfig("[0-9]+");
        allMapConfig.addEntryListenerConfig(new EntryListenerConfig(new MyMapListener("NumberMap"), false, true));
        config.addMapConfig(allMapConfig);

        MapConfig fooAnyMapConfig = new MapConfig("[a-z]+");
        fooAnyMapConfig.addEntryListenerConfig(new EntryListenerConfig(new MyMapListener("LowerMap"), false, true));
        config.addMapConfig(fooAnyMapConfig);

        MapConfig fooMapConfig = new MapConfig("[A-Z]+");
        fooMapConfig.addEntryListenerConfig(new EntryListenerConfig(new MyMapListener("UpperMap"), false, true));
        config.addMapConfig(fooMapConfig);
        
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        IMap<Object, Object> fooMap = instance.getMap("aaa");
        fooMap.put("hoge", "HOGE");

        IMap<Object, Object> barMap = instance.getMap("BBB");
        barMap.put("fuga", "FUGA");
        
        IMap<Object, Object> fooBarMap = instance.getMap("123");
        fooBarMap.put("fizz", "FIZZ");
    }
}
