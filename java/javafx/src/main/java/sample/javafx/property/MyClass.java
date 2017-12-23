package sample.javafx.property;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;

public class MyClass {
    private ReadOnlyDoubleWrapper value = new ReadOnlyDoubleWrapper();
    
    public final double getValue() {
        return this.value.getValue();
    }
    
    public final ReadOnlyDoubleProperty valueProperty() {
        return this.value.getReadOnlyProperty();
    }
    
    public void updateValue() {
        this.value.set(99.9);
    }
}
