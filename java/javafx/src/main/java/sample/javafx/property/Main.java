package sample.javafx.property;

import javafx.beans.property.SimpleDoubleProperty;

public class Main {
    public static void main(String[] args) {
        SimpleDoubleProperty a = new SimpleDoubleProperty();
        SimpleDoubleProperty b = new SimpleDoubleProperty();

        System.out.println(b.get());
        a.set(1.0);
        b.bind(a);

        System.out.println(b.get());
        
        a.set(2.0);

        System.out.println(b.get());
    }
}
