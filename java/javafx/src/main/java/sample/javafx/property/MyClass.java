package sample.javafx.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MyClass {
    private DoubleProperty value = new SimpleDoubleProperty();
    
    public final double getValue() {
        return this.value.getValue();
    }
    
    public final ReadOnlyDoubleProperty valueProperty() {
        return this.value;
    }
}
