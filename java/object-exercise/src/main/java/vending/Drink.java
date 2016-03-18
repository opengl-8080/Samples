package vending;

public class Drink {

    public static final int COKE = 0;
    public static final int DIET_COKE = 1;
    public static final int TEA = 2;

    private int kind;

    public Drink(int kind) {
        this.kind = kind;
    }

    public int getKind() {
        return kind;
    }
}