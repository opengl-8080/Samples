package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_epsilon")
@ToString
@NoArgsConstructor
public class EntityEpsilon implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    public EntityEpsilon(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }
}
