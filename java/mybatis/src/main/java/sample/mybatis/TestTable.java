package sample.mybatis;

import java.io.Serializable;

public class TestTable implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String value;
    
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TestTable [id=" + id + ", value=" + value + "]";
    }
}
