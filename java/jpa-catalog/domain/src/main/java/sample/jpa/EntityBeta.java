package sample.jpa;

import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="table_beta")
@NoArgsConstructor
public class EntityBeta implements Serializable {
    @EmbeddedId
    private EmbeddableId id;

    private String name;

    @ManyToMany(mappedBy="betaList")
    private List<EntityAlpha> alphaList;

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
                ", alphaList=[" + this.alphaList.stream().map(it -> "" + it.id()).collect(java.util.stream.Collectors.joining(", ")) + "]" +
                '}';
    }
}
