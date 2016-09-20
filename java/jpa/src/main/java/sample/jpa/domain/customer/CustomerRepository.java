package sample.jpa.domain.customer;

public interface CustomerRepository {
    Customer find(CustomerCode customerCode);
}
