package sample.domain.order;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@NamedQueries({
    @NamedQuery(name=Order.FIND_ALL, query="SELECT o FROM Order o"),
    @NamedQuery(name=Order.FIND_BY_ORDER_NUMBER, query="SELECT o FROM Order o WHERE o.orderNumber=:orderNumber"),
})
public class Order implements Serializable {
    public static final String FIND_ALL = "Order.findAll";
    public static final String FIND_BY_ORDER_NUMBER = "Order.findByOrderNumber";

    @EmbeddedId
    private OrderNumber orderNumber;
    @JoinColumn(name="customer_code", referencedColumnName="code")
    private Customer customer;
    @Embedded
    private DueDate dueDate;
    @OneToMany
    @JoinColumn(name="order_number", referencedColumnName="order_number")
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

    @Deprecated protected Order() {}
}
