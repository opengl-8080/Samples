package sample.jpa.domain.item;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@ToString
@EqualsAndHashCode
public class ItemName implements Serializable {
    @Column(name="name")
    private String value;

    public ItemName(String value) {
        this.value = value;
    }

    @Deprecated protected ItemName() {}
}
