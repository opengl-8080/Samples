package sample.jpa;

import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name="table_alpha")
@NoArgsConstructor
public class EntityAlpha implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name="alpha_beta_map",
        joinColumns = @JoinColumn(name="table_alpha_id"),
        inverseJoinColumns = @JoinColumn(name="table_beta_id")
    )
    private Map<EmbeddableAlpha, EntityBeta> map;

    public EntityAlpha(String name, Map<EmbeddableAlpha, EntityBeta> map) {
        this.name = name;
        this.map = map;
    }

    public void update(String name) {
        this.name = name;

        int i=0;
        for (EntityBeta entityBeta : this.map.values()) {
            entityBeta.update(name + "[" + i + "]");
            i++;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{id=" + id + ", name=" + name + ", map={");

        String text = this.map.entrySet().stream().map(e -> "[" + e.getKey() + ": " + e.getValue() + "]")
                .collect(java.util.stream.Collectors.joining(", "));

        sb.append(text).append("}}");

        return sb.toString();
    }

    public void delete() {
        java.util.Iterator<Map.Entry<EmbeddableAlpha, EntityBeta>> ite = this.map.entrySet().iterator();
        ite.next();
        ite.remove();
    }
}
