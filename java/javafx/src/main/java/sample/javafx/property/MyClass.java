package sample.javafx.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MyClass {
    private DoubleProperty value = new SimpleDoubleProperty();
    
    public final double getValue() {
        return this.value.get();
    }
    
    public final void setValue(double value) {
        this.value.set(value);
    }

    public DoubleProperty valueProperty() {
        return this.value;
    }
}
