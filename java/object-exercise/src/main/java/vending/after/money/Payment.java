package vending.after.money;

public class Payment {

    private Change change;
    private Coin coin;
    
    public Payment(Coin coin) {
        this.coin = coin;
    }

    public boolean needChange() {
        return this.coin == Coin.FIVE_HUNDRED;
    }

    public boolean doesNotCommitted() {
        return this.coin != null;
    }

    public void commit(CashBox cachBox) {
        if (this.coin == Coin.ONE_HUNDRED) {
            cachBox.add(this.coin);
            this.change = new Change();
        }
        
        if (this.coin == Coin.FIVE_HUNDRED) {
            this.change = cachBox.takeOutChange();
        }
        
        this.coin = null;
    }

    public Change refund() {
        if (this.coin == Coin.ONE_HUNDRED) {
            return new Change();
        }
        
        return this.change;
    }
}
