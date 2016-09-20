package sample.jpa.domain.item;

public interface ItemRepository {
    Item find(ItemCode itemCode);
}
