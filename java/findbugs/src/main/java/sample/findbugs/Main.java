package sample.findbugs;

public class Main {

    public void hoge(int i) {
        new Exception();
    }

    public void fuga(int i) {
        new Exception();
    }

    public void piyo(long l) {
        new Exception();
    }
}