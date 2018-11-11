package sample.hazelcast.shell;

public interface MessageProcessor {
    
    void subscribe(String message);
}
