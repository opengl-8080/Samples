package since9.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Modules {
    private Map<String, Module> moduleMap = new HashMap<>();
    
    synchronized public Module getOrInitialize(String moduleName) {
        return this.moduleMap.computeIfAbsent(moduleName, Module::new);
    }
    
    public void each(Consumer<Module> iterator) {
        this.moduleMap.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .forEach(iterator);
    }
}
