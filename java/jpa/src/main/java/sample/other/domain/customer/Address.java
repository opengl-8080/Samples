package sample.other.domain.customer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Address {
    private String value;

    public Address(String value) {
        this.value = value;
    }

    private Address() {}
}
