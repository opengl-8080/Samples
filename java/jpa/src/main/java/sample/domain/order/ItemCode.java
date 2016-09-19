package sample.domain.order;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@ToString
@EqualsAndHashCode
public class ItemCode implements Serializable {
    @Column(name="code")
    private String value;

    public ItemCode(String value) {
        this.value = value;
    }

    @Deprecated protected ItemCode() {}
}
