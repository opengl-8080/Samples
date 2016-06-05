package domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DueDate {
    private final LocalDate date;

    public DueDate(String textDate) {
        this.date = LocalDate.parse(textDate);
    }

    @Override
    public String toString() {
        return this.date.format(DateTimeFormatter.ISO_DATE);
    }
}
