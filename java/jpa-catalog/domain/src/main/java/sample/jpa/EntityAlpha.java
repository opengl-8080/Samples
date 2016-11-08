package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="alpha_beta_gamma")
@NoArgsConstructor
@ToString
public class EntityAlpha implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="alpha_name")
    protected String name;

    public EntityAlpha(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }

    public void delete() {
    }
}
