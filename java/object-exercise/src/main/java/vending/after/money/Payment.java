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
        return this.isNotCommited()
                ? new Change(this.coin)
                : this.change;
    }

    private boolean isNotCommited() {
        return this.coin != null;
    }
}
