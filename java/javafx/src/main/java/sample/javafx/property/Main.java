package sample.javafx.property;

public class Main {
    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.hogeProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println("observableValue=" + observableValue + ", oldValue=" + oldValue + ", newValue=" + newValue);
        });
        
        myClass.setHoge(2.0);
    }
}
