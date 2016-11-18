package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_beta")
@NoArgsConstructor
@ToString
public class EntityBeta implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name="table_gamma_id")
    private EntityGamma gamma;

    public EntityBeta(String name, EntityGamma gamma) {
        this.name = name;
        this.gamma = gamma;
    }

    public void update(String name) {
        this.name = "Update{" + name + "}";
        this.gamma.update(name);
    }
}
