package sample.oome;

import java.util.ArrayList;
import java.util.List;

public class Main {
    
    
    public static void main(String[] args) {
        List<BigClass> list = new ArrayList<>();
        for (int i=0; i<Integer.parseInt(args[0]); i++) {
            list.add(new BigClass());
            System.out.print("\r" + i);
        }
        System.out.println("end");
    }
    
    private static class BigClass {
        // 1byte * 1024 * 1024 = 1MB
        private byte[][] megaByteData = new byte[1024][1024];
    }
}
