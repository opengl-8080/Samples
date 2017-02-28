package premium.friday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class Main {
    public static void main(String[] args) {
        int[] years = {2017, 2018, 2019};
        for (int year : years) {
            for (int month = 1; month < 13; month++) {
                if (year == 2017 && month == 1) {
                    continue;
                }
                YearMonth yearMonth = YearMonth.of(year, month);
                LocalDate premiumFriday = yearMonth.atDay(1).with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY));
                System.out.println(premiumFriday.format(DateTimeFormatter.ofPattern("yyyy/MM/dd(E)")));
            }
        }
    }
}
