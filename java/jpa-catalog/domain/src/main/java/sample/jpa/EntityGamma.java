package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_gamma")
@ToString
@NoArgsConstructor
public class EntityGamma implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="table_alpha_id")
    private EntityAlpha alpha;

    public EntityGamma(String name, EntityAlpha alpha) {
        this.name = name;
        this.alpha = alpha;
    }

    public void update(String name) {
        this.name = name;
        this.alpha.update(name);
    }
}
