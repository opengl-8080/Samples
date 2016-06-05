package domain;

public class Order {
    private final OrderNumber orderNumber;
    private Customer customer;
    private Item item;
    private Quantity quantity;
    private DueDate dueDate;
    private Destination destination;
    private OrderStatus status;

    public Order(Customer customer, Item item, Quantity quantity, DueDate dueDate, Destination destination) {
        this.orderNumber = new OrderNumber();
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.dueDate = dueDate;
        this.destination = destination;
        this.status = OrderStatus.REQUEST;
    }

    public void toPlan() {
        this.status = OrderStatus.PLAN;
    }
}
