package sample.reladomo;

import java.sql.Timestamp;
import java.time.LocalDate;

public class MyInfinityDateProvider {
    
    public static Timestamp get() {
        LocalDate inifinityDate = LocalDate.of(9999, 12, 31);
        return Timestamp.valueOf(inifinityDate.atStartOfDay());
    }
}
