package sample.javafx.property;

public class Main {
    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        System.out.println(myClass.valueProperty());
        
        myClass.updateValue();

        System.out.println(myClass.valueProperty());
    }
}