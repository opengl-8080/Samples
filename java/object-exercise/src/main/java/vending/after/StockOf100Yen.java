package vending.after;

import java.util.Stack;

public class StockOf100Yen {
    private Stack<Coin> numberOf100Yen = new Stack<>();

    public void add(Coin coin) {
        this.numberOf100Yen.add(coin);
    }

    public int size() {
        return this.numberOf100Yen.size();
    }

    public Coin pop() {
        return this.numberOf100Yen.pop();
    }
}
