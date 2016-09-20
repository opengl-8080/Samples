package sample.jpa.application.order;

import sample.jpa.domain.order.Order;
import sample.jpa.domain.order.OrderNumber;
import sample.jpa.domain.order.OrderRepository;
import sample.jpa.domain.order.form.OrderForm;

public class ModifyOrderService {

    private OrderRepository orderRepository;

    public void modify(OrderNumber orderNumber, OrderForm orderForm) {
        Order order = this.orderRepository.findWithLock(orderNumber);

        
    }
}
