package since9.model;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Package {
    public final String name;
    private Map<String, Type> typeMap = new ConcurrentHashMap<>();

    public Package(String name) {
        this.name = name;
    }
    
    synchronized public Type getOrInitialize(String path, String typeName, String description) {
        return this.typeMap.computeIfAbsent(path, key -> new Type(path, typeName, description));
    }
    
    public void addNewType(String path, String typeName, String description) {
        this.typeMap.put(path, new Type(path, typeName, description));
    }
    
    public void each(Consumer<Type> iterator) {
        this.typeMap.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .forEach(iterator);
    }
}
