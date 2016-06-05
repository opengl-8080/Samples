package domain;

public class Variety {
    private final String value;

    public Variety(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "品種(" + this.value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variety variety = (Variety) o;

        return value.equals(variety.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
