package sample.javafx.property;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;

public class Main {
    public static void main(String[] args) {
        ObservableList<String> list
            = FXCollections.observableArrayList("aaa", "ccc", "bbb", "eee", "ddd", "fff");
        
        list.addListener((Change<? extends String> change) -> {
            System.out.println("==========================================");
            System.out.println("new list=" + change.getList());
            while (change.next()) {
                System.out.println("permutated=" + change.wasPermutated());
                for (int oldIndex=change.getFrom(); oldIndex<change.getTo(); oldIndex++) {
                    int newIndex = change.getPermutation(oldIndex);
                    System.out.println("old(" + oldIndex + ") -> new(" + newIndex + ")");
                }
            }
        });

        System.out.println("old list=" + list);
        list.sort(String::compareTo);
    }
}
