package sample.jpa;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class EmbeddableId implements Serializable {
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long value;

    public Long getValue() {
        return value;
    }
}
