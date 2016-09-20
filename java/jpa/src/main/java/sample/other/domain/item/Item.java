package sample.other.domain.item;

import lombok.ToString;

@ToString
public class Item {
    private ItemCode code;
    private ItemName name;
    private Price price;

    public Item(ItemCode code, ItemName name, Price price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public ItemCode getCode() {
        return code;
    }

    public Price getPrice() {
        return price;
    }

    public ItemName getName() {
        return name;
    }

    private Item() {}
}
