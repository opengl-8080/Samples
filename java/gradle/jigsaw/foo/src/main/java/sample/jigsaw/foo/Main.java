package sample.jigsaw.foo;

import sample.jigsaw.bar.Bar;

public class Main {
    public static void main(String[] args) {
        String text = new Bar().method(10);
        System.out.println(text);
    }
}
