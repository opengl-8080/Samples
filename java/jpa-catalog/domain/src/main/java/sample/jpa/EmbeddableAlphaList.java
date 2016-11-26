package sample.jpa;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.util.List;

@Embeddable
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class EmbeddableAlphaList implements Serializable {

    @Embedded
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
        name="list_table",
        joinColumns=@JoinColumn(name="table_alpha_id")
    )
    private List<EmbeddableAlpha> list;

    public EmbeddableAlphaList(List<EmbeddableAlpha> list) {
        this.list = list;
    }

    public void update(String name) {
        this.list.set(0, new EmbeddableAlpha(name));
        this.list.remove(1);
    }
}
