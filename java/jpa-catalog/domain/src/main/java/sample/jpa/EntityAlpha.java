package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="table_alpha")
@NoArgsConstructor
@ToString
public class EntityAlpha implements Serializable {
    @EmbeddedId
    private EmbeddableId id;

    private String name;

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(
        name="alpha_beta",
        joinColumns=@JoinColumn(name="table_alpha_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="table_beta_id", referencedColumnName="id")
    )
    private List<EntityBeta> betaList;

    @PrePersist
    private void prePersist() {
        this.id = new EmbeddableId();
    }

    public EntityAlpha(String name, List<EntityBeta> betaList) {
        this.name = name;
        this.betaList = betaList;
    }

    public void update(String name) {
        this.name = name;

        for (int i1 = 0; i1 < betaList.size(); i1++) {
            this.betaList.get(i1).update(name + "[" + i1 + "]");
        }
    }

    public void delete() {
        java.util.Iterator<EntityBeta> ite = this.betaList.iterator();
        ite.next();
        ite.remove();
    }

    EmbeddableId id() {
        return this.id;
    }
}
