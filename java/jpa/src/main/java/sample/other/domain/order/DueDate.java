package sample.other.domain.order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@ToString
@EqualsAndHashCode
public class DueDate {
    private Date date;

    public DueDate(Date date) {
        this.date = date;
    }

    private DueDate() {}
}
