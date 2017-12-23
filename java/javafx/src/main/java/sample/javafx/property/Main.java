package sample.javafx.property;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;

public class Main {
    public static void main(String[] args) {
        ObservableList<String> list
            = FXCollections.observableArrayList(
                    "one", "two", "three", "four", "five", "six", "seven");
        
        list.addListener((Change<? extends String> change) -> {
            System.out.println("==========================================");
            System.out.println("list=" + change.getList());
            while (change.next()) {
                System.out.println("----------------------------------");
                System.out.println("from=" + change.getFrom());
                System.out.println("to=" + change.getTo());
                System.out.println("removed=" + change.getRemoved());
                System.out.println("removedSize=" + change.getRemovedSize());
            }
        });

        list.remove("three");
        list.removeAll("two", "seven");
        list.retainAll("one", "six");
    }
}
