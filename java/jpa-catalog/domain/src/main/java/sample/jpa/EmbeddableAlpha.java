package sample.jpa;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class EmbeddableAlpha implements Serializable {
    @Column(name="alpha")
    private String value;

    @OneToOne(cascade={CascadeType.PERSIST})
    @JoinColumn(name="table_beta_id")
    private EntityBeta beta;

    public EmbeddableAlpha(String value, String name) {
        this.value = value;
        this.beta = new EntityBeta(name);
    }

    public void test(String value, String value2) {
        this.value = value;
        this.beta.update(value2);
    }
}
