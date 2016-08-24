package sample.findbugs;

public class Main {

    public void foo() {
        new Exception();
    }

    public void foo(String text) {
        new Exception();
    }

    public void bar() {
        new Exception();
    }

    public void bar(String text, int i) {
        new Exception();
    }

    public void fizz() {
        new Exception();
    }

    public int buzz() {
        new Exception();
        return 1;
    }
}