package sample.domain.order.form;

import lombok.ToString;
import sample.domain.order.ItemCode;
import sample.domain.order.Quantity;

@ToString
public class OrderDetailForm {
    public ItemCode itemCode;
    public Quantity quantity;

    public OrderDetailForm(ItemCode itemCode, Quantity quantity) {
        this.itemCode = itemCode;
        this.quantity = quantity;
    }
}
