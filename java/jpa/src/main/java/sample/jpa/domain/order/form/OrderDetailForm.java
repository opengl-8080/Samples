package sample.jpa.domain.order.form;

import lombok.ToString;
import sample.jpa.domain.Key;
import sample.jpa.domain.item.ItemCode;
import sample.jpa.domain.order.OrderDetail;
import sample.jpa.domain.order.Quantity;

@ToString
public class OrderDetailForm {
    public Key<OrderDetail> id;
    public ItemCode itemCode;
    public Quantity quantity;

    public OrderDetailForm(Key<OrderDetail> id, ItemCode itemCode, Quantity quantity) {
        this.id = id;
        this.itemCode = itemCode;
        this.quantity = quantity;
    }

    public OrderDetailForm(ItemCode itemCode, Quantity quantity) {
        this.itemCode = itemCode;
        this.quantity = quantity;
    }
}
