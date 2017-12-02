package sample.javafx.property;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Main {
    public static void main(String[] args) {
        DoubleProperty a = new SimpleDoubleProperty(1.0);
        DoubleProperty b = new SimpleDoubleProperty(2.0);

        DoubleBinding sum = a.add(b);

        System.out.println("sum=" + sum);
        System.out.println("sum.get()=" + sum.get());
        System.out.println("sum=" + sum);
        
        a.set(3.0);

        System.out.println("sum=" + sum);
        System.out.println("sum.get()=" + sum.get());
        System.out.println("sum=" + sum);
    }
}
