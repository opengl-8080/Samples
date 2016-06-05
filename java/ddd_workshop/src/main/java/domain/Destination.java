package domain;

public class Destination {
    private final String address;

    public Destination(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "納入先(" + this.address + ")";
    }
}
