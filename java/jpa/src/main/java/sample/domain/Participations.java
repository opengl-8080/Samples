package sample.domain;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Participations implements Serializable {
    @EmbeddedId
    private Id id = new Id();

    private String memo;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name="participation_members",
        inverseJoinColumns = @JoinColumn(name="login_id"),
        joinColumns = @JoinColumn(name="participations_id", referencedColumnName = "id")
    )
    private List<Member> members = new ArrayList<>();
}
