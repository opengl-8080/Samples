package sample.jpa.domain.order;

public interface OrderRepository {
    Order find(OrderNumber orderNumber);
    Order findWithLock(OrderNumber orderNumber);
    void register(Order order);
}
