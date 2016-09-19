package sample.domain.order;

public interface ItemRepository {
    Item find(ItemCode itemCode);
}
