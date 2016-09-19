package sample.domain.order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
public class CustomerName implements Serializable {
    @Column(name="name")
    private String value;

    public CustomerName(String value) {
        this.value = value;
    }

    @Deprecated protected CustomerName() {}
}
