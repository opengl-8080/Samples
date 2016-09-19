package sample.domain.order;

import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="items")
@ToString
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

    ItemCode getCode() {
        return code;
    }

    Price getPrice() {
        return price;
    }

    ItemName getName() {
        return name;
    }

    @Deprecated protected Item() {}
}
