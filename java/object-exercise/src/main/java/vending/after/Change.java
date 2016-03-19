package vending.after;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Change implements Cloneable {
    private List<Coin> coins;
    
    public Change() {
        this.coins = new ArrayList<>();
    }

    public Change(List<Coin> coins) {
        this.coins = new ArrayList<>(coins);
    }

    public void add(Coin coin) {
        this.coins.add(coin);
    }

    public void add(Change change) {
        this.coins.addAll(change.coins);
    }

    public int getAmount() {
        return this.coins.stream().collect(Collectors.summingInt(Coin::getAmount));
    }

    public void clear() {
        this.coins.clear();
    }
    
    @Override
    public Change clone() {
        return new Change(this.coins);
    }
}
