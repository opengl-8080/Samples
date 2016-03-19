package vending.after;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Change {
    private List<Coin> coins = new ArrayList<>();
    
    public Change() {}

    public Change(Coin payment) {
        this.coins.add(payment);
    }

    public Change(List<Coin> coins) {
        this.coins.addAll(coins);
    }

    public int getAmount() {
        return this.coins.stream().collect(Collectors.summingInt(Coin::getAmount));
    }
}
