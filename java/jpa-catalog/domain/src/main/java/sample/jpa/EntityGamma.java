package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@ToString
@NoArgsConstructor
public class EntityGamma extends EntityBeta implements Serializable {
    @Column(name="gamma_value")
    private String value;

    public EntityGamma(String name, String code, String value) {
        super(name, code);
        this.value = value;
    }

    @Override
    public void update(String name) {
        this.name = "UPDATE'" + name + "'";
        this.code = "UPDATE\"" + name + "\"";
        this.value = "UPDATE|" + name + "|";
    }
}
