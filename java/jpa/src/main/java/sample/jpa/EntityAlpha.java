package sample.jpa;

import lombok.AllArgsConstructor;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="table_alpha")
@ToString
@NoArgsConstructor @AllArgsConstructor
public class EntityAlpha implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
        name="list_table",
        joinColumns=@JoinColumn(name="table_alpha_id")
    )
    @Column(name="value")
    @OrderBy("DESC")
    private List<String> stringList;

    public EntityAlpha(List<String> stringList) {
        this.stringList = stringList;
    }
}
