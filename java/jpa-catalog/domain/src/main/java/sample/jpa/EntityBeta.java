package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;
import sample.Id;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_beta")
@NoArgsConstructor
@ToString
public class EntityBeta implements Serializable {
    @EmbeddedId
    private Id<EntityBeta> id;

    private String name;

    @PrePersist
    private void setupId() {
        this.id = new Id<>();
    }

    public EntityBeta(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = "Update{" + name + "}";
    }
}
