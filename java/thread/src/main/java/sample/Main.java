package sample;

public class Main {
    public static void main(String[] args) throws Exception {
        Main m = new Main();

        new Thread(() -> {
            for (int i=0; i<100; i++) {
                System.out.println("m.i=" + m.i);
            }
        }).start();
        
        new Thread(() -> {
            while (m.i == 10) {
                continue;
            }
        }).start();

        System.out.println("done");
    }
    
    private int i;
}
