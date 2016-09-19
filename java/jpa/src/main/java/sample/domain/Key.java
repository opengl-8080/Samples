package sample.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
public class Key<T> implements Serializable {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long value;

    public Key(Long value) {
        this.value = value;
    }

    @Deprecated protected Key() {}
}
