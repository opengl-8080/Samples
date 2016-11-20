package order;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//@Entity
//@Table(name="order_requests")
@ToString
public class OrderRequest implements Serializable {
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

//    @Embedded
    private DeliveryDate deliveryDate;

//    @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
//    @JoinTable(
//        name="order_request_relations",
//        joinColumns=@JoinColumn(name="order_requests_id"),
//        inverseJoinColumns=@JoinColumn(name="order_request_details_id")
//    )
    private List<OrderRequestDetail> orderRequestDetails;

    public OrderRequest(DeliveryDate deliveryDate, List<OrderRequestDetail> orderRequestDetails) {
        this.deliveryDate = deliveryDate;
        this.orderRequestDetails = orderRequestDetails;
    }

    protected OrderRequest() {}

    public void update() {
        this.deliveryDate = new DeliveryDate(new Date());
        this.orderRequestDetails.forEach(details -> {
            details.update();
        });
    }
}
