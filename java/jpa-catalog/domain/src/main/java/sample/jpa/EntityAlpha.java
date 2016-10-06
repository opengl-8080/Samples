package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="table_alpha")
@ToString
@NoArgsConstructor
public class EntityAlpha implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
        name="list_table",
        joinColumns=@JoinColumn(name="table_alpha_id")
    )
    private List<EmbeddableAlpha> list;

    public EntityAlpha(String name, List<EmbeddableAlpha> list) {
        this.name = name;
        this.list = list;
    }

    public void test() {
        this.list.remove(0);
        this.list.add(new EmbeddableAlpha("abc"));
    }
}
