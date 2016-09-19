package sample.domain.order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
public class CustomerCode implements Serializable {
    @Column(name="code")
    private String value;

    public CustomerCode(String value) {
        this.value = value;
    }

    @Deprecated protected CustomerCode() {}

    public String getValue() {
        return value;
    }
}
