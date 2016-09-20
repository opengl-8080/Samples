package sample.other.domain.customer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CustomerName {
    private String value;

    public CustomerName(String value) {
        this.value = value;
    }

    private CustomerName() {}
}
