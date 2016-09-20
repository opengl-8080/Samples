package sample.other.domain.order;

public interface OrderRepository {
    Order find(OrderNumber orderNumber);
    void register(Order order);
}
