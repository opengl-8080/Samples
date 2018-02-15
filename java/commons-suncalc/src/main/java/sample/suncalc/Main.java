package sample.suncalc;

import org.shredzone.commons.suncalc.MoonIllumination;
import org.shredzone.commons.suncalc.MoonPosition;
import org.shredzone.commons.suncalc.SunTimes;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.Date;
import java.util.TimeZone;

public class Main {
    public static void main(String[] args) throws Exception {
        double longitude = 135.4833;
        double latitude = 34.6833;
        
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get("moon-angle.txt")))) {
            writer.println("Date\tPhase\tAge");
            
            LocalDate start = LocalDate.of(2018, 1, 1);
            LocalDate end = LocalDate.of(2019, 1,1);
            LocalDate date = start;
            while (date.isBefore(end)) {
                MoonIllumination illumination = MoonIllumination.compute().on(toUtilDate(date)).timezone(TimeZone.getDefault()).execute();

                double phase = illumination.getPhase();
                double normalized = phase + 180.0;
                double age = 29.0 * (normalized / 360.0) + 1.0;
                
                writer.printf("%s\t%.2f\t%.2f\n", date, phase, age);

                date = date.plusDays(1);
            }
        }
    }
    
    private static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
    
    private static Date toUtilDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneRules rules = zoneId.getRules();
        ZoneOffset offset = rules.getOffset(localDate.atStartOfDay());
        Instant instant = localDate.atStartOfDay().toInstant(offset);
        return Date.from(instant);
    }
}
