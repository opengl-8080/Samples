package order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
public class Quantity implements Serializable {
    @Column(name="quantity")
    private int value;

    public Quantity(int value) {
        this.value = value;
    }

    protected Quantity() {}
}
