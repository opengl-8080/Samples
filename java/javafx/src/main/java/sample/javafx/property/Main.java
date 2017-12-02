package sample.javafx.property;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Main {
    public static void main(String[] args) {
        DoubleProperty a = new SimpleDoubleProperty(2.0);

        DoubleBinding binding =
                Bindings.divide(
                    Bindings.subtract(
                            Bindings.multiply(
                                    Bindings.add(a, 3)
                                    , 2)
                            , 4.0)
                    , 3.0);

        System.out.println("((2.0 + 3) * 2 - 4.0) / 3.0 = " + binding.get());
        
        a.set(5.0);

        System.out.println("((5.0 + 3) * 2 - 4.0) / 3.0 = " + binding.get());
    }
}
