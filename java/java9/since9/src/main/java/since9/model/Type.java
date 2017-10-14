package since9.model;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Type {
    public final String path;
    public final String name;
    public final String description;
    
    private Map<String, Member> memberMap = new ConcurrentHashMap<>();

    public Type(String path, String name, String description) {
        this.path = path;
        this.name = name;
        this.description = description;
    }
    
    public void addNewMember(String id, String memberName, String description) {
        this.memberMap.put(id, new Member(id, memberName, description));
    }
    
    public void each(Consumer<Member> iterator) {
        this.memberMap.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .forEach(iterator);
    }
}
