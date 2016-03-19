package vending.after;

public class Stock {
    
    private int quantity;
    
    public Stock(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decrement() {
        this.quantity--;
    }
}
