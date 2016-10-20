package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_alpha")
@ToString
@NoArgsConstructor
public class EntityAlpha implements Serializable {
    @EmbeddedId
    private EmbeddableId id;

    private String name;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="beta_id", referencedColumnName="id")
    private EntityBeta beta;

    @PrePersist
    private void prePersist() {
        this.id = new EmbeddableId();
    }

    public EntityAlpha(String name, EntityBeta beta) {
        this.name = name;
        this.beta = beta;
    }

    public void update(String name, String beta) {
        this.name = name;
        this.beta.update(beta);
    }
}
