package sample.jpa.infrastructure.item;

import sample.jpa.domain.item.Item;
import sample.jpa.domain.item.ItemCode;
import sample.jpa.domain.item.ItemRepository;

import javax.persistence.EntityManager;

public class JpaItemRepository implements ItemRepository {

    private EntityManager em;

    public JpaItemRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Item find(ItemCode itemCode) {
        return this.em.find(Item.class, itemCode);
    }
}
