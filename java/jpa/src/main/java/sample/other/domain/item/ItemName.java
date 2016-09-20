package sample.other.domain.item;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class ItemName {
    private String value;

    public ItemName(String value) {
        this.value = value;
    }

    private ItemName() {}
}
