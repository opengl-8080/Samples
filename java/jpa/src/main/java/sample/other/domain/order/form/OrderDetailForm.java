package sample.other.domain.order.form;

import lombok.ToString;
import sample.other.domain.item.ItemCode;
import sample.other.domain.order.Quantity;

@ToString
public class OrderDetailForm {
    public ItemCode itemCode;
    public Quantity quantity;

    public OrderDetailForm(ItemCode itemCode, Quantity quantity) {
        this.itemCode = itemCode;
        this.quantity = quantity;
    }
}
