package sample.domain.order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@ToString
@EqualsAndHashCode
public class OrderNumber implements Serializable {
    @Column(name="order_number")
    private String value;

    public OrderNumber(String value) {
        this.value = value;
    }

    @Deprecated protected OrderNumber() {}

    public String getValue() {
        return value;
    }
}
