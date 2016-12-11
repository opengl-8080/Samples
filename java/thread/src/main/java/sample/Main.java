package sample;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        new Thread(() ->
            printDate(2015, 10, 11)
        ).start();
        
        new Thread(() ->
            printDate(2016, 12, 31)
        ).start();
    }
    
    private static void printDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH,day);
        
        String expected = "Date(" + year + "/" + month + "/" + day + ")";
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String actual = sdf.format(cal.getTime());
        
        System.out.println(expected + " = " + actual);
    }
}
