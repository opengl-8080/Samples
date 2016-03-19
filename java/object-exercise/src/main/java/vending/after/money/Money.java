package vending.after.money;

public class Money {
    
    private final int amount;
    
    public Money(int amount) {
        this.amount = amount;
    }

    public Money add(Money money) {
        return new Money(this.amount + money.amount);
    }

    @Override
    public String toString() {
        return String.valueOf(this.amount);
    }
}
