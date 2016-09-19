package sample.domain.order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@ToString
@EqualsAndHashCode
public class DueDate implements Serializable{
    @Column(name="due_date")
    private Date date;

    public DueDate(Date date) {
        this.date = date;
    }

    @Deprecated protected DueDate() {}
}
