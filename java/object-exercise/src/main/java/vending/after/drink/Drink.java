package vending.after.drink;

public class Drink {

    private DrinkType kind;

    public Drink(DrinkType kind) {
        this.kind = kind;
    }
    
    public boolean isCoke() {
        return this.kind == DrinkType.COKE;
    }
    
    public boolean isDietCoke() {
        return this.kind == DrinkType.DIET_COKE;
    }
    
    public boolean isTea() {
        return this.kind == DrinkType.TEA;
    }
}