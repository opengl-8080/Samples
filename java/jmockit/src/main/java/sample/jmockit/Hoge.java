package sample.jmockit;

public class Hoge {
    
    private Hoge() {
        System.out.println("Hoge Constructor");
    }
    
    @Override
    public String toString() {
        return "HOGE";
    }
}
