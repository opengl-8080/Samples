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
public class EmbeddableAlpha implements Serializable {
    @Column(name="list_value")
    private String value;

    public EmbeddableAlpha(String value) {
        this.value = value;
    }
}
