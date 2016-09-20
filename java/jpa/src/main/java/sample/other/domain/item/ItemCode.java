package sample.other.domain.item;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class ItemCode {
    private String value;

    public ItemCode(String value) {
        this.value = value;
    }

    private ItemCode() {}
}
