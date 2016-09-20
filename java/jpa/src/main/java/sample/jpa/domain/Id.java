package sample.jpa.domain;

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
public class Id<T> implements Serializable {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long value;

    public Id(Long value) {
        this.value = value;
    }

    @Deprecated protected Id() {}
}
