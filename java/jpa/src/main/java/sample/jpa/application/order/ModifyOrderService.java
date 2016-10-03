package sample.jpa.application.order;

import sample.jpa.domain.customer.Customer;
import sample.jpa.domain.customer.CustomerRepository;
import sample.jpa.domain.item.ItemRepository;
import sample.jpa.domain.order.Order;
import sample.jpa.domain.order.OrderNumber;
import sample.jpa.domain.order.OrderRepository;
import sample.jpa.domain.order.form.OrderForm;

public class ModifyOrderService {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ItemRepository itemRepository;

    public void modify(OrderNumber orderNumber, OrderForm orderForm) {
        Order order = this.orderRepository.findWithLock(orderNumber);
        Customer customer = this.customerRepository.find(orderForm.customerCode);
        order.setCustomer(customer);
        order.setDueDate(orderForm.dueDate);
    }
}
