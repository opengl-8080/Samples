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
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="table_alpha")
@NoArgsConstructor
@ToString
public class EntityAlpha implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name="map_table",
        joinColumns=@JoinColumn(name="table_alpha_id")
    )
    @MapKeyJoinColumn(name = "table_beta_id")
    @Column(name="map_value")
    private Map<EntityBeta, String> map = new HashMap<>();
    
    public EntityAlpha(String name) {
        this.name = name;
    }
    
    public void put(EntityBeta beta, String value) {
        this.map.put(beta, value);
    }
    
    public void remove(EntityBeta beta) {
        this.map.remove(beta);
    }
}
