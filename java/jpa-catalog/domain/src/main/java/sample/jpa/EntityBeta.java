package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_beta")
@NoArgsConstructor
public class EntityBeta implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(mappedBy="beta")
    private EntityAlpha alpha;

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
                '}';
    }
}