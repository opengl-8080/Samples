package sample.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Table(name="proposed_dates")
public class ProposedDate implements Serializable {
    @javax.persistence.Id
    @Column(name="id_date")
    private String date;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name="participations",
        joinColumns=@JoinColumn(name="proposed_date")
    )
    @MapKeyColumn(name="time_span")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Time, Participations> map;

    public ProposedDate(String date) {
        this.date = date;
    }

    public void init() {
        this.map = new HashMap<>();
        this.map.put(Time.MORNING, new Participations());
        this.map.put(Time.AFTERNOON, new Participations());
    }

    public ProposedDate() {}
}
