package domain;

public class Customer {
    private final String value;

    public Customer(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "顧客(" + this.value + ")";
    }
}
