package item;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
public class ItemUnitPrice implements Serializable {
    @Column(name="item_unit_price")
    private int value;

    public ItemUnitPrice(int value) {
        this.value = value;
    }

    public ItemUnitPrice plus(ItemUnitPrice other) {
        return new ItemUnitPrice(this.value + other.value);
    }

    protected ItemUnitPrice() {}
}
