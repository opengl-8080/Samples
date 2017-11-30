package sample.javafx.property;

import javafx.beans.property.SimpleDoubleProperty;

public class Main {
    public static void main(String[] args) {
        SimpleDoubleProperty a = new SimpleDoubleProperty();
        
        a.addListener(System.out::println);
        
        a.set(1.0);
        a.set(2.0);
    }
}
