package sample.other.domain.customer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CustomerCode {
    private String value;

    public CustomerCode(String value) {
        this.value = value;
    }

    private CustomerCode() {}

    public String getValue() {
        return value;
    }
}
