package vending.after;

public enum Coin {
    ONE_HUNDRED(100),
    FIVE_HUNDRED(500);
    
    private final int amount;

    private Coin(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
