package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="table_beta")
@ToString
@NoArgsConstructor
public class EntityBeta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    public EntityBeta(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof EntityBeta)
                && Objects.equals(this.id, ((EntityBeta) obj).id);
    }

    @Override
    public int hashCode() {
        return this.id.intValue();
    }
}
