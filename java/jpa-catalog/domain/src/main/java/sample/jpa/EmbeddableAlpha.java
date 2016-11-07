package sample.jpa;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddableAlpha implements Serializable {
    @Column(name="embeddable_value")
    private String value;

    @Override
    public boolean equals(Object obj) {
        System.out.println("EmbeddableAlpha.equals()");
        return (obj instanceof EmbeddableAlpha)
                && Objects.equals(((EmbeddableAlpha) obj).value, this.value);
    }

    @Override
    public int hashCode() {
        System.out.println("EmbeddableAlpha.hashCode()");
        return this.value == null ? 0 : this.value.hashCode();
    }
}
