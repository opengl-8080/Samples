package sample.other.domain.customer;

public interface CustomerRepository {
    Customer find(CustomerCode customerCode);
}
