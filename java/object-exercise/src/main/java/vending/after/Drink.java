package vending.after;

public class Drink {

    private DrinkType kind;

    public Drink(DrinkType kind) {
        this.kind = kind;
    }

    public DrinkType getKind() {
        return kind;
    }
}