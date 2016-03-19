package vending.after.money;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class CashBox {
    private Stack<Coin> numberOf100Yen = new Stack<>();

    public void add(Coin coin) {
        this.numberOf100Yen.add(coin);
    }
    
    public boolean doesNotHaveChange() {
        return this.numberOf100Yen.size() < 4;
    }
    
    public Change takeOutChange() {
        List<Coin> coins = IntStream.range(0, 4)
                            .mapToObj(i -> this.numberOf100Yen.pop())
                            .collect(toList());
        
        return new Change(coins);
    }
}
