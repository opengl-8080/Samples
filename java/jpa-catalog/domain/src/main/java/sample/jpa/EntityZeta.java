package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_zeta")
@ToString
@NoArgsConstructor
public class EntityZeta implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable
    @ManyToOne
    private String name;

    public EntityZeta(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }
}
