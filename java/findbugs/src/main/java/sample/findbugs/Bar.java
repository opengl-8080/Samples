package sample.findbugs;

public class Bar {

    private String value = "";

    public void method() {
        value = value;

        if (value == value) {
            new Exception();
        }
    }
}
