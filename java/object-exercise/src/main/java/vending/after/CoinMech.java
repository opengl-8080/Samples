package vending.after;

public class CoinMech {
    
    private CashBox cachBox = new CashBox();
    private Payment payment;
    
    public CoinMech() {
        for (int i=0; i<10; i++) {
            this.cachBox.add(Coin.ONE_HUNDRED);
        }
    }

    public void put(Coin coin) {
        this.payment = new Payment(coin);
    }

    public boolean doesNotHaveChange() {
        return this.payment.needChange()
                && this.cachBox.doesNotHaveChange();
    }

    public Change refund() {
        return this.payment.refund();
    }
    
    public void commit() {
        this.payment.commit(this.cachBox);
    }
}
