package item;

import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="items")
@ToString
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @AttributeOverride(name="value", column=@Column(name="name"))
    private ItemName name;
    @Embedded
    @AttributeOverride(name="value", column=@Column(name="unit_price"))
    private ItemUnitPrice unitPrice;

    public Item(ItemName name, ItemUnitPrice unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    protected Item() {}

    public ItemName getName() {
        return this.name;
    }

    public ItemUnitPrice getUnitPrice() {
        return this.unitPrice;
    }
}
