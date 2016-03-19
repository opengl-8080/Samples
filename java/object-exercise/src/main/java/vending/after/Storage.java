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

    public boolean doesNotHaveStock(DrinkType kindOfDrink) {
        return this.findStock(kindOfDrink).isEmpty();
    }

    public Drink takeOut(DrinkType kindOfDrink) {
        Stock stock = this.findStock(kindOfDrink);
        stock.decrement();
        return new Drink(kindOfDrink);
    }
    
    private Stock findStock(DrinkType drinkType) {
        return this.stocks.get(drinkType);
    }
}
