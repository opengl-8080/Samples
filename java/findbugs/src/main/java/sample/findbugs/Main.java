package sample.findbugs;

public class Main {
    static class Foo {
        void fizz() {
            new Exception();
        }

        void buzz() {
            new Exception();
        }

        void buzz(String text) {
            new Exception();
        }

        void hoge() {
            new Exception();
        }
    }

    static class Bar {
        void fizz() {
            new Exception();
        }
        void fizz(String text) {
            new Exception();
        }

        void buzz() {
            new Exception();
        }

        void fuga() {
            new Exception();
        }
    }
}