package sample.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SAMPLE_TABLE")
@NamedQueries({
    @NamedQuery(name=Sample.FIND_ALL, query="SELECT sample FROM Sample sample ORDER BY sample.id ASC")
})
public class Sample implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Sample.findAll";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
    
    public Integer getId() {
        return id;
    }
    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "Sample [id=" + id + ", code=" + code + ", name=" + name + "]";
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
