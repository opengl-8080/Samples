package sample.domain;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class SampleId {
    
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private final Integer id;

    public SampleId(int id) {
        this.id = id;
    }
    
    SampleId() {
        this.id = null;
    }
    
    @Override
    public String toString() {
        return "SampleId [id=" + id + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof SampleId)) return false;
        
        SampleId other = (SampleId) obj;
        
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
