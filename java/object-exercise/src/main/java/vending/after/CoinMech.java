package vending.after;

public class CoinMech {
    
    private StockOf100Yen stockOf100Yen = new StockOf100Yen();
    private Change change = new Change();
    
    public CoinMech() {
        for (int i=0; i<10; i++) {
            this.stockOf100Yen.add(Coin.ONE_HUNDRED);
        }
    }

    public void addChange(Coin payment) {
        this.change.add(payment);
    }

    public void addChange(Change change) {
        this.change.add(change);
    }

    public void addStockOf100Yen(Coin payment) {
        this.stockOf100Yen.add(payment);
    }

    public boolean doesNotHaveChange() {
        return this.stockOf100Yen.doesNotHaveChange();
    }

    public Change takeOutChange() {
        return this.stockOf100Yen.takeOutChange();
    }

    public Change refund() {
        Change result = change.clone();
        change.clear();
        return result;
    }
}
