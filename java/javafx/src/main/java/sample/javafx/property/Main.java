package sample.javafx.property;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener.Change;
import javafx.collections.ObservableMap;

public class Main {
    public static void main(String[] args) {
        ObservableMap<String, String> map = FXCollections.observableHashMap();
        map.put("foo", "FOO");
        map.put("bar", "BAR");
        
        map.addListener((Change<? extends String, ? extends String> change) -> {
            System.out.println("==============================");
            System.out.println("map=" + change.getMap());
            System.out.println("key=" + change.getKey());
            System.out.println("added=" + change.wasAdded());
            System.out.println("valueAdded=" + change.getValueAdded());
            System.out.println("removed=" + change.wasRemoved());
            System.out.println("valueRemoved=" + change.getValueRemoved());
        });
        
        map.put("fizz", "FIZZ");
        map.put("bar", "BARBAR");
        map.remove("foo");
    }
}