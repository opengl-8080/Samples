package sample.jpa;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class EmbeddableBeta implements Serializable {
    @Column(name="beta_value")
    private String value;

    public EmbeddableBeta(String value) {
        this.value = value;
    }
}
