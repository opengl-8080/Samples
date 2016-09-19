package sample.infrastructure.order;

import sample.domain.order.Order;
import sample.domain.order.OrderNumber;
import sample.domain.order.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class JpaOrderRepository implements OrderRepository {

    private EntityManager em;

    @Override
    public Order find(OrderNumber orderNumber) {
        TypedQuery<Order> query = this.em.createNamedQuery(Order.FIND_BY_ORDER_NUMBER, Order.class);
        query.setParameter("orderNumber", orderNumber);
        return query.getSingleResult();
    }

    @Override
    public void register(Order order) {
        this.em.persist(order);
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
