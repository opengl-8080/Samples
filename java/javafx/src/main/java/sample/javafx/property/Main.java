package sample.javafx.property;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class Main {
    public static void main(String[] args) {
        ObservableList<String> list = FXCollections.observableArrayList("one", "two", "three");
        FilteredList<String> filteredList = list.filtered(e -> e.contains("e"));

        System.out.println("list=" + list);
        System.out.println("filteredList=" + filteredList);
        
        list.addAll("four", "five");

        System.out.println("list=" + list);
        System.out.println("filteredList=" + filteredList);

        filteredList.setPredicate(e -> e.contains("f"));

        System.out.println("list=" + list);
        System.out.println("filteredList=" + filteredList);
    }
}