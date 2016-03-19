package vending.after.stock;

public class Stock {
    
    private int quantity;
    
    public Stock(int quantity) {
        this.quantity = quantity;
    }

    public void decrement() {
        this.quantity--;
    }
    
    public boolean isEmpty() {
        return this.quantity == 0;
    }
}
