public class Main {

    public static void main(String[] args) {
        Hoge hoge = new Fuga();
        hoge.method();

        Fuga fuga = new Fuga();
        fuga.method();
    }

    public interface Hoge {
        @Deprecated
        void method();
    }

    public static class Fuga implements Hoge {

        @Override
        public void method() {}
    }
}
