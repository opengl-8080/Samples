package sample.domain.order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
public class Address implements Serializable {
    @Column(name="address")
    private String value;

    public Address(String value) {
        this.value = value;
    }

    @Deprecated protected Address() {}
}
