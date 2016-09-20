package sample.jpa.infrastructure.order;

import sample.jpa.domain.order.Order;
import sample.jpa.domain.order.OrderNumber;
import sample.jpa.domain.order.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

public class JpaOrderRepository implements OrderRepository {

    private EntityManager em;

    public JpaOrderRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Order find(OrderNumber orderNumber) {
        return this.em.find(Order.class, orderNumber);
    }

    @Override
    public Order findWithLock(OrderNumber orderNumber) {
        return this.em.find(Order.class, orderNumber, LockModeType.WRITE);
    }

    @Override
    public void register(Order order) {
        this.em.persist(order);
    }
}
