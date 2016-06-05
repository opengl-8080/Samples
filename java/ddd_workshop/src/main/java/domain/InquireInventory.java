package domain;

import java.util.HashMap;
import java.util.Map;

public class InquireInventory {

    private final Map<Item, Inventory> inventories = new HashMap<>();

    public boolean existsStock(Item item) {
        Inventory inventory = this.inventories.get(item);
        return inventory.existsStock();
    }

    public void initialzie(Item item, Quantity quantity) {
        Inventory inventory = new Inventory(item, quantity);
    }

    public void reserve(Item item, Quantity quantity) {
        this.inventories.get(item).decrease(quantity);
    }
}
