package sample.javafx.property;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public class Main {
    public static void main(String[] args) {
        ObservableList<String> list = FXCollections.observableArrayList("one", "two", "three");
        SortedList<String> sortedList = list.sorted();

        System.out.println("list=" + list);
        System.out.println("sortedList=" + sortedList);
        
        list.addAll("four", "five", "six");

        System.out.println("list=" + list);
        System.out.println("sortedList=" + sortedList);
    }
}