package sample.other.domain.item;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode
public class Price {
    private BigDecimal value;

    public Price(BigDecimal value) {
        this.value = value;
    }

    private Price() {}
}
