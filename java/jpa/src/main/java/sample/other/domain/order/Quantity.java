package sample.other.domain.order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Quantity {
    private int value;

    public Quantity(int value) {
        this.value = value;
    }

    private Quantity() {}
}
