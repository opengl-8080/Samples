package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_alpha")
@ToString
@NoArgsConstructor
public class EntityAlpha implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private EmbeddableAlpha foo;
    @Embedded
    @AttributeOverride(name="value", column=@Column(name="bar"))
    private EmbeddableAlpha bar;

    public EntityAlpha(EmbeddableAlpha foo, EmbeddableAlpha bar) {
        this.foo = foo;
        this.bar = bar;
    }

    public void test() {
        this.foo = new EmbeddableAlpha("FOO");
        this.bar = new EmbeddableAlpha("BAR");
    }
}
