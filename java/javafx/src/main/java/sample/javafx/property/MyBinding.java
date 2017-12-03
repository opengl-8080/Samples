package sample.javafx.property;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;

public class MyBinding extends DoubleBinding {
    private final DoubleProperty source;

    public MyBinding(DoubleProperty source) {
        this.bind(source);
        this.source = source;
    }

    @Override
    protected double computeValue() {
        return ((this.source.get() + 3) * 2 - 4.0) / 3.0;
    }
}