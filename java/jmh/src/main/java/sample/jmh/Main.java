package sample.jmh;

public class Main {

//    private static volatile String s;
    
    public static void main(String[] args) {
        
        for (int i=0; i<100_000; i++) {
            Main m = new Main();
            String s = m.execute();
        }
    }
    
    private String execute() {
        String text = "";
        
        for (int i=0; i<100; i++) {
            text += String.valueOf(i);
        }
        
        return text;
    }
}
