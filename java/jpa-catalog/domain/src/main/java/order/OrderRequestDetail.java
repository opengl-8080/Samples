package order;

import item.Item;
import item.ItemName;
import item.ItemUnitPrice;
import lombok.ToString;

import java.io.Serializable;

//@Entity
//@Table(name="order_request_details")
@ToString
public class OrderRequestDetail implements Serializable {
//    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
//    @OneToOne
//    @JoinColumn(name="items_id")
    private Item item;
//    @Embedded
    private ItemName itemName;
//    @Embedded
    private ItemUnitPrice itemUnitPrice;
//    @Embedded
    private Quantity quantity;

    public OrderRequestDetail(Item item, Quantity quantity) {
        this.item = item;
        this.itemName = item.getName();
        this.itemUnitPrice = item.getUnitPrice();
        this.quantity = quantity;
    }

    protected OrderRequestDetail() {}

    public void update() {
        this.quantity = new Quantity(111);
    }
}
