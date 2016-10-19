package sample.jpa;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_beta")
@NoArgsConstructor
public class EntityBeta implements Serializable {
    @Id
    @Column(name="key_1")
    private String key1;

    @Id
    @Column(name="key_2")
    private String key2;

    private String name;

    public EntityBeta(String key1, String key2, String name) {
        this.key1 = key1;
        this.key2 = key2;
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EntityBeta{" +
                "key1='" + key1 + '\'' +
                ", key2='" + key2 + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
