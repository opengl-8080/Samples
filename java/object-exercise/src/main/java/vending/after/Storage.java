package vending.after;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private Map<DrinkType, Stock> stocks = new HashMap<>();
    
    public Storage() {
        this.stocks.put(DrinkType.COKE, new Stock(5));
        this.stocks.put(DrinkType.DIET_COKE, new Stock(5));
        this.stocks.put(DrinkType.TEA, new Stock(5));
    }

    public boolean isEmpty(DrinkType kindOfDrink) {
        return this.stocks.get(kindOfDrink).isEmpty();
    }

    public void decrement(DrinkType kindOfDrink) {
        this.stocks.get(kindOfDrink).decrement();
    }
}
