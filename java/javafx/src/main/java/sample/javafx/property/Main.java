package sample.javafx.property;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;

public class Main {
    public static void main(String[] args) {
        ObservableList<String> list = FXCollections.observableArrayList("one", "two", "three");
        
        list.addListener((Change<? extends String> change) -> {
            System.out.println("==========================================");
            while (change.next()) {
                System.out.println("list=" + change.getList());
                if (change.wasAdded()) {
                    System.out.println("追加 : " + change);
                }
                if (change.wasRemoved()) {
                    System.out.println("削除 : " + change);
                }
                if (change.wasPermutated()) {
                    System.out.println("順序変更 : " + change);
                }
                if (change.wasReplaced()) {
                    System.out.println("置換 : " + change);
                }
            }
        });

        list.add("FOO");
        list.remove("one");
        FXCollections.sort(list);
        list.set(1, "hoge");
    }
}
