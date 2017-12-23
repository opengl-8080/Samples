package sample.javafx.property;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ObservableList<String> list = FXCollections.observableArrayList("one", "two", "three");
        
        list.addListener((Change<? extends String> change) -> {
            System.out.println("==========================================");
            while (change.next()) {
                System.out.println("list=" + change.getList());
                System.out.println("from=" + change.getFrom());
                System.out.println("to=" + change.getTo());
                System.out.println("addedSubList=" + change.getAddedSubList());
                System.out.println("addedSize=" + change.getAddedSize());
            }
        });

        list.add("FOO");
        list.addAll(Arrays.asList("hoge", "fuga"));
    }
}
