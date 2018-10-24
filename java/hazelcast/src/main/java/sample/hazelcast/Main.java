package sample.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class Main {
    
    public static void main(String[] args) {
        Config config = new Config();

        MapConfig allMapConfig = new MapConfig("*");
        allMapConfig.addEntryListenerConfig(new EntryListenerConfig(new MyMapListener("AllMap"), false, true));
        config.addMapConfig(allMapConfig);

        MapConfig fooAnyMapConfig = new MapConfig("foo*");
        fooAnyMapConfig.addEntryListenerConfig(new EntryListenerConfig(new MyMapListener("FooAny"), false, true));
        config.addMapConfig(fooAnyMapConfig);

        MapConfig fooMapConfig = new MapConfig("foo");
        fooMapConfig.addEntryListenerConfig(new EntryListenerConfig(new MyMapListener("OnlyFoo"), false, true));
        config.addMapConfig(fooMapConfig);

        MapConfig barMapConfig = new MapConfig("bar");
        barMapConfig.addEntryListenerConfig(new EntryListenerConfig(new MyMapListener("OnlyBar"), false, true));
        config.addMapConfig(barMapConfig);
        
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        IMap<Object, Object> fooMap = instance.getMap("foo");
        fooMap.put("hoge", "HOGE");

        IMap<Object, Object> barMap = instance.getMap("bar");
        barMap.put("fuga", "FUGA");
        
        IMap<Object, Object> fooBarMap = instance.getMap("fooBar");
        fooBarMap.put("fizz", "FIZZ");
    }
}
