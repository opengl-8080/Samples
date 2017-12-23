package sample.javafx.property;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

public class MyClass {
    private ListProperty<String> list = new SimpleListProperty<>();

    public void setList(ObservableList<String> list) {
        this.list.set(list);
    }

    public ObservableList<String> getList() {
        return list.get();
    }

    public ListProperty<String> listProperty() {
        return list;
    }
}
