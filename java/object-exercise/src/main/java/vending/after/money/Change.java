package vending.after.money;

import java.util.ArrayList;
import java.util.List;

public class Change {
    private List<Coin> coins = new ArrayList<>();
    
    public Change() {}

    public Change(Coin payment) {
        this.coins.add(payment);
    }

    public Change(List<Coin> coins) {
        this.coins.addAll(coins);
    }

    public Money getAmount() {
        return this.coins.stream()
                    .map(Coin::toMoney)
                    .reduce(new Money(0), (sum, money) -> sum.add(money));
    }
}
