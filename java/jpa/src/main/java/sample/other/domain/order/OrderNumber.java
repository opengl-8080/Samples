package sample.other.domain.order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class OrderNumber {
    private String value;

    public OrderNumber(String value) {
        this.value = value;
    }

    private OrderNumber() {}

    public String getValue() {
        return value;
    }
}
