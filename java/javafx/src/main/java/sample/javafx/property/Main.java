package sample.javafx.property;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;

public class Main {
    public static void main(String[] args) {
        SimpleDoubleProperty a = new SimpleDoubleProperty();
        SimpleDoubleProperty b = new SimpleDoubleProperty();
        a.set(1.0);
        b.set(2.0);

        DoubleBinding sum = a.add(b);
        System.out.println(sum.get());

        a.set(5.0);
        System.out.println(sum.get());
    }
}
