package sample.doma2;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming=NamingType.SNAKE_LOWER_CASE)
public class TestTable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private MyDomain value;
    
    public TestTable() {
    }
    
    public TestTable(String value) {
        this.value = new MyDomain(value);
    }
    
    public void setValue(String value) {
        this.value = new MyDomain(value);
    }

    @Override
    public String toString() {
        return "TestTable [id=" + id + ", value=" + value + "]";
    }
}
