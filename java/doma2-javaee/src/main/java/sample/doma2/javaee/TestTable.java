package sample.doma2.javaee;

import org.seasar.doma.Entity;

@Entity
public class TestTable {
    
    private Long id;
    private String value;
    
    @Override
    public String toString() {
        return "TestTable [id=" + id + ", value=" + value + "]";
    }
}
