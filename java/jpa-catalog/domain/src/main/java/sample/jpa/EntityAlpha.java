package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_alpha")
@NoArgsConstructor
@ToString
public class EntityAlpha implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="alpha_name")
    protected String name;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="table_epsilon_id")
    protected EntityEpsilon epsilon;

    public EntityAlpha(String name, EntityEpsilon epsilon) {
        this.name = name;
        this.epsilon = epsilon;
    }

    public void update(String name) {
        this.name = name;
        this.epsilon.update(name);
    }

}
