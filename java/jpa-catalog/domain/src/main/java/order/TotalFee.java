package order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class TotalFee {
    private final int value;

    public TotalFee(int value) {
        this.value = value;
    }
}
