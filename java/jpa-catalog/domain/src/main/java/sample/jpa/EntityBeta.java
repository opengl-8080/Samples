package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_beta")
@NoArgsConstructor
@ToString(callSuper = true)
public class EntityBeta extends EntityAlpha implements Serializable {
    @Column(name="beta_code")
    protected String code;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="table_zeta_id")
    protected EntityZeta zeta;

    public EntityBeta(String name, EntityEpsilon epsilon, String code, EntityZeta zeta) {
        super(name, epsilon);
        this.code = code;
        this.zeta = zeta;
    }

    @Override
    public void update(String name) {
        this.name = "Update{" + name + "}";
        this.code = "Update[" + name + "]";
        this.zeta.update(name);
    }
}
