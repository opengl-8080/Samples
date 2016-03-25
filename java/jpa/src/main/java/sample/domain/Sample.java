package sample.domain;

import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SAMPLE_TABLE")
@NamedQuery(name="Sample.findAll", query="SELECT sample FROM Sample sample ORDER BY sample.id ASC")
public class Sample {
    
    @EmbeddedId
    private final SampleId id = new SampleId();
    @Embedded
    private SampleValue value;
    
    public void setValue(SampleValue value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Sample [id=" + id + ", value=" + value + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Sample)) return false;
        
        Sample other = (Sample)obj;
        
        return Objects.equals(this.id, other.id);
    }
}
