package sample.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Parent implements Serializable {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="parent_id", insertable=true)
    private List<Child> list;
}
