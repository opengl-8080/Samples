package sample.other.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Key<T> {
    private Long value;

    public Key(Long value) {
        this.value = value;
    }

    private Key() {}
}
