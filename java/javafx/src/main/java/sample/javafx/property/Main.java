package sample.javafx.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Main {
    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        DoubleProperty readOnlyDoubleProperty = (DoubleProperty)myClass.valueProperty();
        readOnlyDoubleProperty.set(20.0);

        System.out.println(myClass.getValue());
    }
}
