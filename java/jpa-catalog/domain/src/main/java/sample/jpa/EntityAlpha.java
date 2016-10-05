package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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

    @PrePersist
    void setupId() {
        this.id = new EmbeddableId();
    }

    public EntityAlpha(String name) {
        this.name = name;
    }
}
