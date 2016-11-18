package item;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
public class ItemName implements Serializable {
    @Column(name="item_name")
    private String value;

    public ItemName(String value) {
        this.value = value;
    }

    protected ItemName() {}
}
