package sample;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static Map<String, Integer> map = new ConcurrentHashMap<>();
    
    public static void main(String[] args) throws Exception {
        new Thread(() ->
            add(1)
        ).start();
        
        new Thread(() ->
            add(2)
        ).start();
        
        Thread.sleep(100);

        System.out.println(map.get("foo"));
    }
    
    synchronized private static void add(int n) {
        Integer total = map.getOrDefault("foo", 0);
        System.out.println("total = " + total + ", n = " + n);
        map.put("foo", total + n);
    }
}
