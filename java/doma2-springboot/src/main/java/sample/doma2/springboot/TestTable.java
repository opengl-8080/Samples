package sample.doma2.springboot;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming=NamingType.SNAKE_UPPER_CASE)
public class TestTable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String value;
    
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TestTable [id=" + id + ", value=" + value + "]";
    }
}
