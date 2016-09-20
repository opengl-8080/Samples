package sample.other.domain.order;

import lombok.ToString;
import sample.other.domain.Key;
import sample.other.domain.item.Item;
import sample.other.domain.item.ItemCode;
import sample.other.domain.item.ItemName;
import sample.other.domain.item.Price;

@ToString
public class OrderDetail {
    private Key<OrderDetail> id;
    private ItemCode itemCode;
    private Price price;
    private ItemName itemName;
    private Quantity quantity;

    public OrderDetail(Key<OrderDetail> id, ItemCode itemCode, Price price, ItemName itemName, Quantity quantity) {
        this.id = id;
        this.itemCode = itemCode;
        this.price = price;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public OrderDetail(Item item, Quantity quantity) {
        this.itemCode = item.getCode();
        this.price = item.getPrice();
        this.itemName = item.getName();
        this.quantity = quantity;
    }

    private OrderDetail() {}
}
