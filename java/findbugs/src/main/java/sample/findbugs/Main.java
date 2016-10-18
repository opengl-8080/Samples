package sample.findbugs;

public class Main {

    public void method() {
        new Exception();
    }

    private static class InnerClass {

        public void method() {
            new Exception();
        }
    }
}