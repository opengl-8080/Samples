package sample.jpa.domain.customer;

import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@ToString
@Table(name="customers")
public class Customer implements Serializable {
    @EmbeddedId
    private CustomerCode code;
    @Embedded
    private CustomerName name;
    @Embedded
    private Address address;

    public Customer(CustomerCode code, CustomerName name, Address address) {
        this.code = code;
        this.name = name;
        this.address = address;
    }

    @Deprecated protected Customer() {}

    public boolean is(CustomerCode customerCode) {
        return this.code.equals(customerCode);
    }
}
