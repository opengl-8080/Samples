package sample.domain.order;

public interface CustomerRepository {
    Customer find(CustomerCode customerCode);
}
