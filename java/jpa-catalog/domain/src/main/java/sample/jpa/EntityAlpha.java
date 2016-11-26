package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;
import sample.Id;

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
@NoArgsConstructor
@ToString
public class EntityAlpha implements Serializable {
    @EmbeddedId
    private Id<EntityAlpha> id;

    private String name;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(
        name="table_beta_id",
        referencedColumnName="id"
    )
    private EntityBeta beta;
    
    @PrePersist
    private void setupId() {
        this.id = new Id<>();
    }

    public EntityAlpha(String name, EntityBeta beta) {
        this.name = name;
        this.beta = beta;
    }

    public void update(String name) {
        this.name = "update(" + name + ")";
        this.beta.update(name);
    }

}
