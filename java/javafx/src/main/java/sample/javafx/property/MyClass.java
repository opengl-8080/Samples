package sample.javafx.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MyClass {
    private DoubleProperty hoge = new SimpleDoubleProperty();
    
    public double getHoge() {
        return this.hoge.get();
    }
    
    public void setHoge(double hoge) {
        this.hoge.set(hoge);
    }

    public DoubleProperty hogeProperty() {
        return this.hoge;
    }
}
