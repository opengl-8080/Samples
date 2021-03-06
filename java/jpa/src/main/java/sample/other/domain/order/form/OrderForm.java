package sample.other.domain.order.form;

import lombok.ToString;
import sample.other.domain.customer.CustomerCode;
import sample.other.domain.order.DueDate;

import java.util.ArrayList;
import java.util.List;

@ToString
public class OrderForm {
    public CustomerCode customerCode;
    public DueDate dueDate;
    public List<OrderDetailForm> orderDetailForms = new ArrayList<>();

    public OrderForm(CustomerCode customerCode, DueDate dueDate) {
        this.customerCode = customerCode;
        this.dueDate = dueDate;
    }
}
