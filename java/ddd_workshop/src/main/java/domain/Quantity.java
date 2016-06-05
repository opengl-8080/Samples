package domain;

public class Quantity {
    public static final Quantity ZERO = new Quantity(0);

    private final int value;

    public Quantity(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value + "個";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quantity quantity = (Quantity) o;

        return value == quantity.value;

    }

    @Override
    public int hashCode() {
        return value;
    }

    public Quantity minus(Quantity quantity) {
        int value = this.value - quantity.value;
        return new Quantity(value);
    }
}
