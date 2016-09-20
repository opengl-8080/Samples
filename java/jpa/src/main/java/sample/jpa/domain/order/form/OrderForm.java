package sample.jpa.domain.order.form;

import lombok.ToString;
import sample.jpa.domain.customer.CustomerCode;
import sample.jpa.domain.order.DueDate;

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
