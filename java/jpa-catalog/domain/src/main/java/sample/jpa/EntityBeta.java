package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_beta")
@ToString
@NoArgsConstructor
public class EntityBeta implements Serializable {
    @EmbeddedId
    private EmbeddableId id;

    private String name;

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

    public long getLongId() {
        return this.id.getValue();
    }
}
