package sample.spring.shell;

public class Hoge {
    
    private final String value;

    public Hoge(String value) {
        this.value = value;
    }
    
    public void hello() {
        System.out.println("Hoge(" + this.value + ")");
    }

    @Override
    public String toString() {
        return this.value;
    }
}
