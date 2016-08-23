package sample.findbugs;

public class Main {

    private String value = "";

    public void method() {
        value = value;

        if (value == value) {
            new Exception();
        }
    }

    private static class InnerClass {

        private String value = "";

        public void method() {
            value = value;

            if (value == value) {
                new Exception();
            }
        }
    }
}