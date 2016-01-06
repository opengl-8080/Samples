package sample.doma2;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming=NamingType.SNAKE_LOWER_CASE)
public class TestTable {
    
    public int id;
    public String value;
    
    @Override
    public String toString() {
        return "TestTable [id=" + id + ", value=" + value + "]";
    }
}
