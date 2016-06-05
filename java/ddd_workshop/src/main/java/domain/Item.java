package domain;

public class Item {
    private final Variety variety;
    private final ItemPackage itemPackage;

    public Item(Variety variety, ItemPackage itemPackage) {
        this.variety = variety;
        this.itemPackage = itemPackage;
    }

    @Override
    public String toString() {
        return "商品(" + this.variety + ", " + this.itemPackage + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!variety.equals(item.variety)) return false;
        return itemPackage.equals(item.itemPackage);

    }

    @Override
    public int hashCode() {
        int result = variety.hashCode();
        result = 31 * result + itemPackage.hashCode();
        return result;
    }
}
