package vending.after.money;

public enum Coin {
    ONE_HUNDRED(100),
    FIVE_HUNDRED(500);
    
    private final int amount;

    private Coin(int amount) {
        this.amount = amount;
    }
    
    public Money toMoney() {
        return new Money(this.amount);
    }
}
