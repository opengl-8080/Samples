package sample.hazelcast;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.map.impl.MapListenerAdapter;

public class MyMapListener extends MapListenerAdapter<String, String> {
    
    private final String name;

    public MyMapListener(String name) {
        this.name = name;
    }

    @Override
    public void entryAdded(EntryEvent<String, String> event) {
        super.entryAdded(event);
        System.out.println("Listener=" + this.name + ", Map=" + event.getName() + ", Added={" + event.getKey() + ":" + event.getValue() + "}");
    }
}
