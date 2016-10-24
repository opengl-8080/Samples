package sample.jpa;

import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_beta")
@NoArgsConstructor
public class EntityBeta implements Serializable {
    @EmbeddedId
    private EmbeddableId id;

    private String name;

    @ManyToOne
    @JoinColumn(name="alpha_id", referencedColumnName="id")
    private EntityAlpha alpha;

    @PrePersist
    private void prePersist() {
        this.id = new EmbeddableId();
    }

    public EntityBeta(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EntityBeta{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alpha=" + alpha.id() +
                '}';
    }
}
