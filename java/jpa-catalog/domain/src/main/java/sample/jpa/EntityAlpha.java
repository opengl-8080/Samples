package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="table_alpha")
@NoArgsConstructor
public class EntityAlpha implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name="alpha_id")
    private List<EntityBeta> betaList;

    public EntityAlpha(String name, EntityBeta... beta) {
        this.name = name;
        this.betaList = Arrays.asList(beta);
    }

    public void update(String name, String... beta) {
        this.name = name;

        for (int i = 0; i < beta.length; i++) {
            this.betaList.get(i).update(beta[i]);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{id=" + id + ", name=" + name + ", betaList=[");

        String text = this.betaList.stream().map(EntityBeta::toString).collect(Collectors.joining(", "));
        sb.append(text).append("]}");

        return sb.toString();
    }
}
