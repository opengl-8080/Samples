package sample.hazelcast;

import com.hazelcast.core.MapStore;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MyMapStore implements MapStore<String, String> {
    private Map<String, String> db = new ConcurrentHashMap<>(Map.of(
            "foo", "FOO",
            "bar", "BAR"
    ));
    
    @Override
    public String load(String key) {
        return this.db.get(key);
    }

    @Override
    public Map<String, String> loadAll(Collection<String> keys) {
        return this.db
                .entrySet()
                .stream()
                .filter(e -> keys.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Iterable<String> loadAllKeys() {
        return new HashSet<>(this.db.keySet());
    }

    @Override
    public void store(String key, String value) {
        this.db.put(key, value);
    }

    @Override
    public void storeAll(Map<String, String> map) {
        this.db.putAll(map);
    }

    @Override
    public void delete(String key) {
        this.db.remove(key);
    }

    @Override
    public void deleteAll(Collection<String> keys) {
        keys.forEach(this.db::remove);
    }
}
