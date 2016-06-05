package domain;

public class Inventory {

    private final Item item;
    private Quantity quantity;

    public Inventory(Item item, Quantity quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public boolean existsStock() {
        return this.quantity.equals(Quantity.ZERO);
    }

    public void decrease(Quantity quantity) {
        this.quantity = this.quantity.minus(quantity);
    }
}
