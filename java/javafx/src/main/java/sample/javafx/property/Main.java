package sample.javafx.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Main {
    public static void main(String[] args) {
        DoubleProperty a = new SimpleDoubleProperty();
        DoubleProperty b = new SimpleDoubleProperty();
        
        System.out.println("a=" + a + ", b=" + b);

        a.bind(b);

        System.out.println("a=" + a + ", b=" + b);
        
        b.set(3.0);

        System.out.println("a=" + a + ", b=" + b);
        System.out.println("a=" + a.get() + ", b=" + b);
    }
}
