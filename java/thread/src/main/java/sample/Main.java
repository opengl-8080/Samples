package sample;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
    
    public static void main(String[] args) {
        new Thread(() ->
            printDate(2015, 10, 11)
        ).start();
        
        new Thread(() ->
            printDate(2016, 12, 31)
        ).start();
    }
    
    private static void printDate(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);

        String expected = "Date(" + year + "/" + month + "/" + day + ")";
        String actual = formatter.format(date);
        
        System.out.println(expected + " = " + actual);
    }
}
