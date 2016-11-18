package order;

import item.Item;
import item.ItemName;
import item.ItemUnitPrice;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="order_details")
@ToString
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="items_id")
    private Item item;
    @Embedded
    private ItemName itemName;
    @Embedded
    private ItemUnitPrice itemUnitPrice;
    @Embedded
    private Quantity quantity;

    public OrderDetail(Item item, Quantity quantity) {
        this.item = item;
        this.itemName = item.getName();
        this.itemUnitPrice = item.getUnitPrice();
        this.quantity = quantity;
    }

    protected OrderDetail() {}

    public void update() {
        this.quantity = new Quantity(111);
    }
}
