package sample.jpa.domain.order;

import sample.jpa.domain.customer.Customer;
import sample.jpa.domain.customer.CustomerRepository;
import sample.jpa.domain.item.Item;
import sample.jpa.domain.item.ItemRepository;
import sample.jpa.domain.order.form.OrderDetailForm;
import sample.jpa.domain.order.form.OrderForm;

public class OrderService {

    private GenerateOrderNumberService generateOrderNumberService;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ItemRepository itemRepository;

    public OrderService(GenerateOrderNumberService generateOrderNumberService, OrderRepository orderRepository, CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.generateOrderNumberService = generateOrderNumberService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    public void register(OrderForm orderForm) {
        OrderNumber nextOrderNumber = this.generateOrderNumberService.generateNextNumber();
        Customer customer = this.customerRepository.find(orderForm.customerCode);
        Order order = new Order(nextOrderNumber, customer, orderForm.dueDate);

        for (OrderDetailForm orderDetailForm : orderForm.orderDetailForms) {
            Item item = this.itemRepository.find(orderDetailForm.itemCode);
            order.addOrderDetail(item, orderDetailForm.quantity);
        }

        this.orderRepository.register(order);
    }

    public void modify(OrderNumber orderNumber, OrderForm orderForm) {

    }


}
