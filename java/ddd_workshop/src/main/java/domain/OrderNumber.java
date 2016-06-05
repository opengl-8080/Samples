package domain;

public class OrderNumber {
    private static int SEQUENCE = 0;

    private final String number;

    public OrderNumber() {
        this.number = "" + (++SEQUENCE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderNumber that = (OrderNumber) o;

        return number.equals(that.number);

    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
