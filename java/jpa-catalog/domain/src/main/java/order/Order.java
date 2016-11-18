package order;

import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "Orders")
@Table(name="orders")
@ToString
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private DeliveryDate deliveryDate;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="orders_id", nullable=false)
    private List<OrderDetail> orderDetails;

    public Order(DeliveryDate deliveryDate, List<OrderDetail> orderDetails) {
        this.deliveryDate = deliveryDate;
        this.orderDetails = orderDetails;
    }

    protected Order() {}

    public void update() {
        this.deliveryDate = new DeliveryDate(new Date());
        this.orderDetails.forEach(details -> {
            details.update();
        });
    }
}
