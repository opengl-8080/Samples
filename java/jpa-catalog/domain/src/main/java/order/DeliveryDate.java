package order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@EqualsAndHashCode
@ToString
public class DeliveryDate implements Serializable {
    @Column(name="delivery_date")
    private Date date;

    public DeliveryDate(Date date) {
        this.date = date;
    }

    protected DeliveryDate() {}
}
