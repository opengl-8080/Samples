package sample.jpa.domain.order;

import lombok.ToString;
import sample.jpa.domain.Id;
import sample.jpa.domain.item.Item;
import sample.jpa.domain.item.ItemCode;
import sample.jpa.domain.item.ItemName;
import sample.jpa.domain.item.Price;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="order_details")
@ToString
public class OrderDetail implements Serializable {
    @EmbeddedId
    private Id<OrderDetail> id;
    @Embedded
    @AttributeOverride(name="value", column=@Column(name="item_code"))
    private ItemCode itemCode;
    @Embedded
    private Price price;
    @Embedded
    @AttributeOverride(name="value", column=@Column(name="item_name"))
    private ItemName itemName;
    @Embedded
    private Quantity quantity;

    public OrderDetail(Id<OrderDetail> id, ItemCode itemCode, Price price, ItemName itemName, Quantity quantity) {
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

    @Deprecated protected OrderDetail() {}
}
