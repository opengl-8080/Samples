package sample.jpa;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DateValue implements Serializable {
    @Temporal(TemporalType.DATE)
    @Column(name="date_value")
    private Date value;

    @Override
    public boolean equals(Object obj) {
        System.out.println("DateValue.equals(" + obj + ")");
        return (obj instanceof DateValue)
                && ((DateValue) obj).value.equals(this.value);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
