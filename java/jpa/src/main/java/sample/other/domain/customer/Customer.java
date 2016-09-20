package sample.other.domain.customer;

import lombok.ToString;

@ToString
public class Customer {
    private CustomerCode code;
    private CustomerName name;
    private Address address;

    public Customer(CustomerCode code, CustomerName name, Address address) {
        this.code = code;
        this.name = name;
        this.address = address;
    }

    private Customer() {}
}
