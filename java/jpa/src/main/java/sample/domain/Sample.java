package sample.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SAMPLE_TABLE")
@NamedQuery(name="Sample.findAll", query="SELECT sample FROM Sample sample ORDER BY sample.id ASC")
public class Sample {

    @Id
    private final Long id;
    private final String value;

    public Sample() {
        this.id = null;
        this.value = null;
        System.out.println("private Sample()");
    }

    @Override
    public String toString() {
        return "Sample [id=" + id + ", value=" + value + "]";
    }
}
