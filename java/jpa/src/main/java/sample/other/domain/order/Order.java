package sample.other.domain.order;

import sample.other.domain.customer.Customer;
import sample.other.domain.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private OrderNumber orderNumber;
    private Customer customer;
    private DueDate dueDate;
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order(OrderNumber orderNumber, Customer customer, DueDate dueDate, List<OrderDetail> orderDetails) {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.dueDate = dueDate;
        this.orderDetails = orderDetails;
    }

    public Order(OrderNumber nextOrderNumber, Customer customer, DueDate dueDate) {
        this.orderNumber = nextOrderNumber;
        this.customer = customer;
        this.dueDate = dueDate;
    }

    public void addOrderDetail(Item item, Quantity quantity) {
        OrderDetail orderDetail = new OrderDetail(item, quantity);
        this.orderDetails.add(orderDetail);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (OrderDetail orderDetail : this.orderDetails) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(orderDetail);
        }

        return "Order{" +
                "orderNumber=" + orderNumber +
                ", customer=" + customer +
                ", dueDate=" + dueDate +
                ", orderDetails=" + sb +
                '}';
    }

    private Order() {}
}
