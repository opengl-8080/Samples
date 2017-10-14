package since9.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Module {
    public final String name;
    private Map<String, Package> packageMap = new HashMap<>();

    public Module(String name) {
        this.name = name;
    }
    
    synchronized public Package getOrInitialize(String packageName) {
        return this.packageMap.computeIfAbsent(packageName, Package::new);
    }
    
    public void each(Consumer<Package> iterator) {
        this.packageMap.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .forEach(iterator);
    }
}
