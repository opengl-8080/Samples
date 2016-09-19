package sample.domain.order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@ToString
@EqualsAndHashCode
public class Price implements Serializable {
    @Column(name="price")
    private BigDecimal value;

    public Price(BigDecimal value) {
        this.value = value;
    }

    @Deprecated protected Price() {}
}
