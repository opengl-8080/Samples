package sample.jpa.domain.item;

import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@ToString
@Table(name="items")
public class Item implements Serializable {
    @EmbeddedId
    private ItemCode code;
    @Embedded
    private ItemName name;
    @Embedded
    private Price price;

    public Item(ItemCode code, ItemName name, Price price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    @Deprecated protected Item() {}

    public ItemCode getCode() {
        return code;
    }

    public Price getPrice() {
        return price;
    }

    public ItemName getName() {
        return name;
    }
}
