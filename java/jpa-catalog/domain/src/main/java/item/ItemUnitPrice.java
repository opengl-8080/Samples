package item;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

//@Embeddable
@EqualsAndHashCode
@ToString
public class ItemUnitPrice implements Serializable {
//    @Column(name="item_unit_price")
    private int value;

    public ItemUnitPrice(int value) {
        this.value = value;
    }

    protected ItemUnitPrice() {}

    public int getValue() {
        return value;
    }
}
