package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name="table_alpha")
@ToString
@NoArgsConstructor
public class EntityAlpha implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
        name="map_table",
        joinColumns=@JoinColumn(name="table_alpha_id")
    )
    @Column(name="map_value")
    private Map<EmbeddableAlpha, String> map;

    public EntityAlpha(String name, Map<EmbeddableAlpha, String> map) {
        this.name = name;
        this.map = map;
    }

    public void test() {
        EmbeddableAlpha key = this.map.keySet().iterator().next();
        this.map.remove(key);
        this.map.put(new EmbeddableAlpha("z"), "ZZZ");
    }
}
